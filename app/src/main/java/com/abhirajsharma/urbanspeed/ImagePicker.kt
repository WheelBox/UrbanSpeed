package com.abhirajsharma.urbanspeed

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abhirajsharma.urbanspeed.others.setLocalImage
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_image_picker.*
import java.io.File

class ImagePicker : AppCompatActivity() {

    private var mProfileFile: File? = null
    val TAG = "checkMe"
    private var tempPic: File? = null
    private var mStorageRef: StorageReference? = null
    private lateinit var db: FirebaseFirestore
    private var imgUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)

        mStorageRef = FirebaseStorage.getInstance().reference
        db = FirebaseFirestore.getInstance()

        selectProfilePicture.setOnClickListener {
            pickProfileImage()
        }

        profileCreateBtn.setOnClickListener {
            profileProgressBar.visibility = View.VISIBLE
            when {
                profileUsername.text.isNullOrEmpty() -> {
                    profileRootLayout.showSnackBar("Please choose a username.")
                    profileProgressBar.visibility = View.GONE
                }
                tempPic == null -> {
                    profileRootLayout.showSnackBar("Please choose a picture.")
                    profileProgressBar.visibility = View.GONE
                }
                else -> {
                    uploadPicture(tempPic!!, profileUsername.text.toString())
                }
            }
        }

    }

    private fun pickProfileImage() {
        ImagePicker.with(this)
                .cropSquare()
                .setImageProviderInterceptor { imageProvider -> // Intercept ImageProvider
                    Log.d(TAG, "Selected ImageProvider: " + imageProvider.name)
                }
                .maxResultSize(512, 512)
                .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "Reached in result")
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val file = ImagePicker.getFile(data)!!
                mProfileFile = file
                tempPic = file
                selectProfilePicture.setLocalImage(file, true)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
//                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadPicture(f: File, name: String) {
        Log.d(TAG, "Reached Storage")
        val file = Uri.fromFile(f)!!
        val userName = name.replace(" ", "_") + ".jpg"
        val riversRef = mStorageRef!!.child("UsersProfilePicture/$userName")
        riversRef.putFile(file).addOnSuccessListener {
            Log.d(TAG, "File Upload Done")
            riversRef.downloadUrl.addOnCompleteListener { task ->
                val profileImageUrl = task.result.toString()
                imgUrl = profileImageUrl
                addUserDb(imgUrl)
            }
        }
                .addOnFailureListener {
                    profileProgressBar.visibility = View.GONE
                    Log.d(TAG, "File Upload Failure")
                }
    }

    private fun addUserDb(img: String) {
        val userList: MutableMap<String, Any> = HashMap()
        Log.d(TAG, "Reached Database")
        userList["img"] = img
        val currentUid = FirebaseAuth.getInstance().currentUser!!.uid


        db.collection("USERS").document(currentUid)
                .update(mapOf(
                        "img" to img,
                        "fullname" to profileUsername.text.toString()
                ))
                .addOnSuccessListener {
                    updateUI()
                }
                .addOnFailureListener {
                    Log.w(TAG, "Error adding document" + it.message.toString())
                }


//        db.collection("USERS").document(currentUid)
//                .set(userList)
//                .addOnSuccessListener { documentReference ->
//                    Log.d(TAG, "Database Upload Done")
//                    Log.d(TAG, "DocumentSnapshot added with ID: $documentReference")
//                    updateUI()
//                }
//                .addOnFailureListener { e ->
//                    profileProgressBar.visibility = View.GONE
//                    Log.d(TAG, "Database Upload Failure")
//                    Log.w(TAG, "Error adding document", e)
//                }
    }

    private fun updateUI() {
        profileProgressBar.visibility = View.GONE
        startActivity(Intent(this, SplashScreen::class.java))
    }

    private fun View.showSnackBar(msg: String) {
        val snack = Snackbar.make(this, msg, Snackbar.LENGTH_SHORT)
        snack.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        snack.show()
    }

}