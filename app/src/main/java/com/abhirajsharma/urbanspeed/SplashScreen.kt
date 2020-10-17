package com.abhirajsharma.urbanspeed

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_phone_login2.*
import java.io.IOException
import java.util.*


class SplashScreen : AppCompatActivity() {

    var locationManager: LocationManager? = null
    var loaction: Location? = null
    private val REQUEST_LOCATION = 1
    private val Delay = 2000

    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        DBquaries.chechUSERS()

        if (mAuth.currentUser == null) {
             Handler(Looper.getMainLooper()).postDelayed({
                 startActivity(Intent(this, LoginActivity::class.java))
             }, 1500)
         } else {
            Handler(Looper.getMainLooper()).postDelayed({
                 val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
                 if(DBquaries.admins_list.contains(currentuser)){
                     FirebaseFirestore.getInstance().collection("ADMINS").document(currentuser).get().addOnCompleteListener{
                         if(it.isSuccessful){
                             val id =it.result["store_id"].toString()
                             val i = Intent(this, Products::class.java)
                             i.putExtra("store_id",id)
                             startActivity(i)
                             finish()
                         }
                     }
                 }
                 if(DBquaries.users_list.contains(currentuser)){
                     DBquaries.findDistance()
                     DBquaries.setShop()
                     startActivity(Intent(this, MainActivity::class.java))
                 }




             }, 3000)
        }
    }




}

