package com.abhirajsharma.urbanspeed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhirajsharma.urbanspeed.adapter.WishListAdapter
import com.abhirajsharma.urbanspeed.model.WishListModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_wish_list.view.*

class WishListFrag : Fragment() {

    private val TAG = "checkMe"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wish_list, container, false)

        val wishListModelsList = arrayListOf<WishListModel>()
        val wishListAdapter = WishListAdapter(activity as AppCompatActivity, wishListModelsList)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        view.wishListRv!!.layoutManager = linearLayoutManager
        view.wishListRv!!.adapter = wishListAdapter
        wishListAdapter.notifyDataSetChanged()
        FirebaseFirestore.getInstance().collection("WISHLIST")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        wishListModelsList.add(WishListModel(
                                document.data["name"].toString(),
                                document.data["inStock"].toString(),
                                document.data["price"].toString(),
                                document.data["img"].toString(),
                                document.data["rating"].toString().toInt(),
                                document.data["logo"].toString()
                        ))
                    }
                    wishListAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }

        return view
    }
}