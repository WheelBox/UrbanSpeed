package com.abhirajsharma.urbanspeed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)


//        toolbar = findViewById( R.id.toolbar_profile );
//        ChangeAddressLayout = view.findViewById<LinearLayout>(R.id.myAccountChangeAddressLayout)
//        myCart = view.findViewById<LinearLayout>(R.id.myAccountCartLL)
//        myOrderLayout = view.findViewById<LinearLayout>(R.id.myOrderLayout)

//        myOffers = view.findViewById<LinearLayout>(R.id.myAccountOfferLayout)
//        myAccoutnFav = view.findViewById<LinearLayout>(R.id.myAccountFavouriteLayout)


        view.myAccountFavouriteLayout.setOnClickListener(View.OnClickListener { })


        view.myOrderLayout.setOnClickListener {
            val intent = Intent(activity, myOrder::class.java)
            startActivity(intent)
        }
        view.myAccountOfferLayout.setOnClickListener {

        }

        view.myAccountCartLL.setOnClickListener {
            val intent = Intent(activity, MyCart::class.java)
            startActivity(intent)
        }

        view.myAccountChangeAddressLayout.setOnClickListener {
            val intent = Intent(activity, MyAddress::class.java)
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