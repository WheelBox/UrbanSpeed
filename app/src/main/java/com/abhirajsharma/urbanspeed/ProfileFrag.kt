package com.abhirajsharma.urbanspeed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFrag : Fragment() {

//    private var myOrderLayout: LinearLayout? = null
//    private var ChangeAddressLayout: LinearLayout? = null
//    private var myAccountbottomLayout: LinearLayout? = null
//    private var myCart: LinearLayout? = null
//    private var myOffers: LinearLayout? = null
//    private var myAccoutnFav: LinearLayout? = null
//    private var ordrOnDemand: LinearLayout? = null
//    private var help_ll: LinearLayout? = null

    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)


//        toolbar = findViewById( R.id.toolbar_profile );
//        ChangeAddressLayout = view.findViewById<LinearLayout>(R.id.myAccountChangeAddressLayout)
//        myCart = view.findViewById<LinearLayout>(R.id.myAccountCartLL)
//        myOrderLayout = view.findViewById<LinearLayout>(R.id.myOrderLayout)

//        myOffers = view.findViewById<LinearLayout>(R.id.myAccountOfferLayout)
//        myAccoutnFav = view.findViewById<LinearLayout>(R.id.myAccountFavouriteLayout)


        view.myAccountFavouriteLayout.setOnClickListener {

        }

        view.profile_user_name.text = auth.currentUser?.displayName.toString()
        view.profile_user_mail.text = auth.currentUser?.email.toString()
        Glide.with(this).load(auth.currentUser?.photoUrl.toString()).into(view.profile_image)

        view.myOrderLayout.setOnClickListener {
            val intent = Intent(activity, myOrder::class.java)
            startActivity(intent)
        }
        view.myAccountOfferLayout.setOnClickListener {

        }

        view.myAccountCartLL.setOnClickListener {
           // val intent = Intent(activity, MyCart::class.java)
            val intent = Intent(activity, UserDetails::class.java)
                 startActivity(intent)
        }

        view.myAccountChangeAddressLayout.setOnClickListener {
            val intent = Intent(activity, MyAddress::class.java)
//
            startActivity(intent)
        }

        return view
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
//            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}