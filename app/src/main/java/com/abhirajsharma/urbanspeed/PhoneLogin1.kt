package com.abhirajsharma.urbanspeed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_phone_login2.*

class PhoneLogin1 : AppCompatActivity() {

    val TAG = "checkMe"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_login2)

        vendorBtn.setOnClickListener {
            val countryCode = "+" + ccp.selectedCountryCode
            val num = countryCode + phone_et.text.toString()
            validateNum(num, "vendor")
            Log.d(TAG, "number: $num")
        }

        userBtn.setOnClickListener {
            val countryCode = "+" + ccp.selectedCountryCode
            val num = countryCode + phone_et.text.toString()
            validateNum(num, "user")
            Log.d(TAG, "number: $num")
        }

        backPhoneBtn.setOnClickListener {
            finish()
        }

    }

    private fun validateNum(num: String, accountType: String) {
        if (phone_et.text.isNullOrEmpty()) {
            phoneLayout2.showSnackBar("The field cannot be empty.")
        } else {
            val i = Intent(this, PhoneLogin2::class.java)
            i.putExtra("fullPhoneNum", num)
            i.putExtra("phoneNum", phone_et.text.toString())
            i.putExtra("accountType", accountType)
            startActivity(i)
        }
    }

    private fun View.showSnackBar(msg: String) {
        val snack = Snackbar.make(this, msg, Snackbar.LENGTH_SHORT)
        snack.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        snack.show()
    }
}