package com.abhirajsharma.urbanspeed

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SplashScreen: AppCompatActivity() {

    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser==null){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
            }, 1500)
        }else{


            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().uid.toString()).get()
                    .addOnSuccessListener { result ->

                        val lat=result.get("lat").toString()
                        val lon=result.get("lon").toString()
                        DBquaries.findDistance(lat,lon)
                    }

            DBquaries.shopModelList.clear()
            DBquaries.setShop() 



            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
            }, 1400)
        }
    }

}