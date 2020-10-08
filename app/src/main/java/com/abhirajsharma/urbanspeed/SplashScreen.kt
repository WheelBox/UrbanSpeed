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
import java.io.IOException
import java.util.*


class SplashScreen: AppCompatActivity() {

    var locationManager: LocationManager? = null
    var loaction:Location?=null
    private val REQUEST_LOCATION = 1

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
            DBquaries.findDistance("28.414497825686833","77.29039451247131")
            DBquaries.shopModelList.clear()
            DBquaries.setShop()


            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                OnGPS()
            } else {
                getLocation()
            }





            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
            }, 1400)
        }
    }
    private fun OnGPS() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false)
                .setPositiveButton("Yes") { dialog, which -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                .setNegativeButton("No") {
                    ///updating location
                    dialog, which -> dialog.cancel() }
        val alertDialog = builder.create()
        alertDialog.show()
    }
    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                        applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
        } else {

            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val criteria = Criteria()
            val bestProvider = locationManager.getBestProvider(criteria, false)
            var location = locationManager.getLastKnownLocation(bestProvider!!)
            val loc_listener: LocationListener = object : LocationListener {
                override fun onLocationChanged(l: Location) {}
                override fun onProviderEnabled(p: String) {}
                override fun onProviderDisabled(p: String) {}
                override fun onStatusChanged(p: String, status: Int, extras: Bundle) {}
            }
            locationManager
                    .requestLocationUpdates(bestProvider, 0, 0f, loc_listener)
            location = locationManager.getLastKnownLocation(bestProvider)
            try {
                val lat = location!!.latitude
                val lon = location.longitude
                val geocoder: Geocoder
                val addresses: List<Address>
                geocoder = Geocoder(this, Locale.getDefault())
                addresses = geocoder.getFromLocation(lat, lon, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                val address = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                Toast.makeText(applicationContext, address, Toast.LENGTH_SHORT).show()
            } catch (e: NullPointerException) {
                val stat=e.localizedMessage
                Toast.makeText(applicationContext, stat, Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
            }
        }
    }



}

