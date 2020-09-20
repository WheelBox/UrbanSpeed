package com.abhirajsharma.urbanspeed

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_new_address.*


class NewAddress : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    val TAG = "checkMe"
    private val user: HashMap<String, Any> = HashMap()
    private val privateUser: HashMap<String, Any> = HashMap()
    private val auth by lazy { FirebaseAuth.getInstance() }
    private var x = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_address)

        db = FirebaseFirestore.getInstance()

//        x = getLastAddressIndex()
        save_address_btn.setOnClickListener {

            val pinCode = add_address_pin_et.text.toString()
            val address = add_address_details_et.text.toString()
            val city = add_address_city_et.text.toString()
            val state = add_address_state_et.text.toString()

            user["name"] = add_address_name_et.text.toString()
            privateUser["name"] = auth.currentUser?.displayName.toString()
            user["mobile"] = add_address_number_et.text.toString()
            user["altMobile"] = add_address_alternate_no_et.text.toString()
            user["address"] = "$address, $city, $state $pinCode"
            user["city"] = add_address_city_et.text.toString()
            user["state"] = add_address_state_et.text.toString()
            user["landmark"] = add_address_LandMark_et.text.toString()
            if (address_type_Home.isChecked) {
                user["addressType"] = "Home Address"
            } else {
                user["addressType"] = "Work/Office Address"
            }
            if (add_address_name_et.text!!.isEmpty() || add_address_number_et.text!!.isEmpty() ||
                    add_address_pin_et.text!!.isEmpty() || add_address_details_et.text!!.isEmpty() ||
                    add_address_city_et.text!!.isEmpty() || add_address_state_et.text!!.isEmpty()) {
                Toast.makeText(this, "Please fill all necessary fields.", Toast.LENGTH_SHORT).show()
            } else if (!address_type_Home.isChecked && !address_type_office.isChecked) {
                Toast.makeText(this, "Please select an Address Type.", Toast.LENGTH_SHORT).show()
            } else {
                addData(user)
            }
        }

    }

//    private fun getLastAddressIndex(): Int {
//        db.collection("userAddress").document(auth.currentUser?.uid.toString()).collection("address$x")
//        db.collection("userAddress").document(auth.currentUser?.uid.toString())
//                .collection("address$x").orderBy("index").get()
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        for (documentSnapshot in task.result!!) {
//                            homeCategoryModelsList.add(HomeCategoryModels(
//                                    documentSnapshot["image"].toString(),
//                                    documentSnapshot["title"].toString(),
//                                    documentSnapshot["tag"].toString()))
//                        }
//                        homeCategoryAdapter.notifyDataSetChanged()
//                    }
//                }
//    }

    private fun addData(user: MutableMap<String, Any>) {
        x++
//        Log.d("checkMe", "doc $docID")
        db.collection("userAddress").document(auth.currentUser?.uid.toString())         //auth.currentUser?.uid.toString()
                .set(privateUser)
                .addOnSuccessListener {
                }
                .addOnFailureListener { e ->
                }

        db.collection("userAddress").document(auth.currentUser?.uid.toString()).collection("address$x")
                .add(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Address Added.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
    }

}
