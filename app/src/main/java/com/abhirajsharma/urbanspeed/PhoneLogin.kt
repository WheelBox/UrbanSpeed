package com.abhirajsharma.urbanspeed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_phone_login.*
import java.util.*
import java.util.concurrent.TimeUnit


class PhoneLogin : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    var verificationId: String = ""
    private val KEY_VERIFICATION_ID = "key_verification_id"
    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var countryCode:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_login)

//        setSupportActionBar(app_bar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
//        app_bar.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//        }

        otpBtn.setOnClickListener {
            countryCode = "+" + ccp.selectedCountryCode
            phoneProgressBar.visibility = View.VISIBLE
            verify()
        }

        verifyBtn.setOnClickListener {
            Log.d("checkMe", "otp: " + otp_et.text.toString())
            phoneProgressBar.visibility = View.VISIBLE
            authenticate()
        }

    }

    private fun verificationCallbacks(){
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(p0)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                phoneProgressBar.visibility = View.GONE
                    if (e is FirebaseAuthInvalidCredentialsException) {
                        if (phone_et.text?.isEmpty()!!){
                            phoneLayout.showSnackBar("The field cannot be empty.")
                        }else {
                            phoneLayout.showSnackBar("The format of the phone number provided is incorrect.")
                        }
                    } else if (e is FirebaseTooManyRequestsException) {
                        phoneLayout.showSnackBar(e.localizedMessage!!.toString())
                    }
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                verificationId = p0
            }
        }
    }

    private fun authenticate() {
        val verificationNum = otp_et.text.toString()
        val credential = PhoneAuthProvider.getCredential(verificationId, verificationNum)
        Log.d("checkMe", credential.toString())
        signInWithPhoneAuthCredential(credential)
//        9971439072
    }

    private fun verify() {
        verificationCallbacks()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                countryCode + phone_et.text.toString(),
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) {
            if (it.isSuccessful){
                phoneLayout.showSnackBar("Account Created Successfully.")


                //  firebaseAuth.signInWithEmailAndPassword( mail,password  );
                val userData: MutableMap<String, Any> = HashMap()
                userData["is_valid"] = true
                userData["fullname"] = ""
                userData["image"] = ""
                userData["mail"] = ""
                userData["lat"] = ""
                userData["lon"] = ""
                userData["password"] = ""
                userData["permanent_phone"] =phone_et.text.toString()
                userData["phone"] = phone_et.text.toString()
                userData["previous_position"] = 0
                userData["address_details"] = ""
                userData["address_type"] = ""

                val currentuser = FirebaseAuth.getInstance().currentUser!!.uid


                FirebaseFirestore.getInstance().collection("USERS").document(currentuser).set(userData).addOnCompleteListener {
                    val listSize: MutableMap<String, Any> = HashMap()
                    listSize["list_size"] = 0

                    FirebaseFirestore.getInstance().collection("USERS").document(currentuser).collection("USER_DATA").document("MY_ADDRESS").set(listSize)
                            .addOnCompleteListener { }
                    FirebaseFirestore.getInstance().collection("USERS").document(currentuser).collection("USER_DATA").document("MY_GROCERY_CARTITEMCOUNT").set(listSize)
                            .addOnCompleteListener { }

                    FirebaseFirestore.getInstance().collection("USERS").document(currentuser).collection("USER_DATA").document("MY_GROCERY_ORDERS").set(listSize)
                            .addOnCompleteListener { }
                    FirebaseFirestore.getInstance().collection("USERS").document(currentuser).collection("USER_DATA").document("MY_GROCERY_WISHLIST").set(listSize)
                            .addOnCompleteListener {

                                val Size: MutableMap<String, Any> = HashMap()
                                Size["list_size"] = 0
                                Size["store_id"] = 0

                                FirebaseFirestore.getInstance().collection("USERS").document(currentuser).collection("USER_DATA").document("MY_GROCERY_CARTLIST").set(Size)
                                        .addOnCompleteListener { }
                            }
                }








                phoneProgressBar.visibility = View.GONE
                startActivity(Intent(this, SplashScreen::class.java))
                finish()
            }
        }.addOnFailureListener {
            phoneLayout.showSnackBar("Error : ${it.localizedMessage}")
        }
    }

    private fun View.showSnackBar(msg: String) {
        val snack = Snackbar.make(this, msg, Snackbar.LENGTH_SHORT)
        snack.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        snack.show()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
        return
    }

}

