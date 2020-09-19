package com.abhirajsharma.urbanspeed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.abhirajsharma.urbanspeed.adapter.TodayOffersAdapter
import com.abhirajsharma.urbanspeed.model.dealsofthedayModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_offers.view.*

class OffersFrag : Fragment() {

    val TAG = "checkME"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_offers, container, false)

        val offersList = arrayListOf<dealsofthedayModel>()
        val offersAdapter = TodayOffersAdapter(activity as AppCompatActivity, offersList)
        val gridLayoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        view.offersRv!!.layoutManager = gridLayoutManager
        view.offersRv!!.adapter = offersAdapter
        offersAdapter.notifyDataSetChanged()

        FirebaseFirestore.getInstance().collection("TODAYSOFFERS")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        offersList.add(dealsofthedayModel(
                                document.data["image"].toString(),
                                document.data["title"].toString(),
                                document.data["des"].toString(),
                                document.data["price"].toString(),
                                document.data["id"].toString(),
                                document.data["tag"].toString()))
                    }
                    offersAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }

        return view
    }
}