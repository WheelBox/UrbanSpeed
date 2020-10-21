package com.abhirajsharma.urbanspeed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
import java.util.concurrent.TimeUnit


class PhoneLogin2 : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    var verificationId: String = ""
    private val KEY_VERIFICATION_ID = "key_verification_id"
    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var fullPhoneNum = ""
    var phoneNum = ""
    var accountType = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_login)

        fullPhoneNum = intent.getStringExtra("fullPhoneNum")!!
        phoneNum = intent.getStringExtra("phoneNum")!!
        accountType = intent.getStringExtra("accountType")!!

        val displayingText = resources.getString(R.string.numberFilling) + phoneNum
        verify(fullPhoneNum)

        fillingNumText.text = displayingText
//        setSupportActionBar(app_bar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

//        app_bar.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//        }

//        otpBtn.setOnClickListener {
//            countryCode = "+" + ccp.selectedCountryCode
//            phoneProgressBar.visibility = View.VISIBLE
//            verify()
//        }

        verifyBtn.setOnClickListener {
            Log.d("checkMe", "otp: " + otp_et.text.toString())
            hideSoftKeyboard(this, phoneLayout)
            phoneProgressBar.visibility = View.VISIBLE
            when {
                otp_et.text.isEmpty() -> {
                    phoneLayout.showSnackBar("Please enter the OTP received through SMS")
                }
                otp_et.text.length < 6 -> {
                    phoneLayout.showSnackBar("Please enter a valid OTP")
                }
                else -> {
                    authenticate()
                }
            }
        }

//        backOtpBtn.setOnClickListener {
//            finish()
//        }

    }

    private fun hideSoftKeyboard(activity: Activity, view: View) {
        val imm: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }

    private fun verificationCallbacks() {
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(p0)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                phoneProgressBar.visibility = View.GONE
                if (e is FirebaseAuthInvalidCredentialsException) {
                    phoneLayout.showSnackBar("The format of the phone number provided is incorrect.")
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

    private fun verify(phone: String) {
        verificationCallbacks()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks)
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                otp_et.setText(credential.smsCode.toString())

                updateUI()
                phoneProgressBar.visibility = View.GONE

                finish()
            }
        }.addOnFailureListener {
            phoneLayout.showSnackBar("Error : ${it.localizedMessage}")
        }
    }

    private fun updateUI() {
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        phoneProgressBar.visibility = View.VISIBLE

        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().currentUser!!.uid).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documentSnapshot = task.result
                        if (documentSnapshot.exists()) {
                            phoneProgressBar.visibility = View.GONE
                            DBquaries.findDistance()
                            DBquaries.setShop()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }else{
                            FirebaseFirestore.getInstance().collection("ADMINS").document(FirebaseAuth.getInstance().currentUser!!.uid).get()
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val documentSnapshot = task.result
                                            if (documentSnapshot.exists()) {
                                                phoneProgressBar.visibility = View.GONE
                                                val id = task.result["store_id"].toString()
                                                val i = Intent(this, Products::class.java)
                                                i.putExtra("store_id", id)
                                                startActivity(i)
                                                finish()

                                            }else{
                                                phoneProgressBar.visibility = View.GONE
                                                if (accountType == "vendor") {
                                                    DBquaries.setAdminDATA(this)
                                                } else if (accountType == "user") {
                                                    DBquaries.setUserData()
                                                    val userData: MutableMap<String, Any> = HashMap()
                                                    userData["fullname"] = ""
                                                    userData["lat"] = ""
                                                    userData["lon"] = ""
                                                    userData["permanent_phone"] = phoneNum
                                                    userData["phone"] = phoneNum
                                                    userData["previous_position"] = 0
                                                    userData["address_details"] = ""
                                                    userData["address_type"] = ""
                                                    userData["img"] = ""
                                                    val currentuser = FirebaseAuth.getInstance().currentUser!!.uid

                                                    FirebaseFirestore.getInstance().collection("USERS").document(currentuser).set(userData).addOnCompleteListener {
                                                        if(it.isSuccessful){
                                                            startActivity(Intent(this, UserDetails::class.java))
                                                            finish()
                                                        }
                                                    }



                                                }
                                            }
                                        }
                                    }

                        }
                    }
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

