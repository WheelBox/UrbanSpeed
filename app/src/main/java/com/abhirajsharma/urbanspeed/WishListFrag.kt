package com.abhirajsharma.urbanspeed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.abhirajsharma.urbanspeed.adapter.WishListAdapter
import com.abhirajsharma.urbanspeed.model.WishListModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_wish_list.view.*

class WishListFrag : Fragment() {

    private val TAG = "checkMe"
    val bullet: String = "\u2022 "
    var totalItems = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wish_list, container, false)

        val wishListModelsList = arrayListOf<WishListModel>()
        val wishListAdapter = WishListAdapter(activity as AppCompatActivity, wishListModelsList)
        val layoutManager = GridLayoutManager(activity, 2)
        view.wishListRv!!.layoutManager = layoutManager
        view.wishListRv!!.itemAnimator = DefaultItemAnimator()
        val hDivider = DividerItemDecoration(activity, DividerItemDecoration.HORIZONTAL)
        val vDivider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        hDivider.setDrawable(ContextCompat.getDrawable(activity as AppCompatActivity, R.drawable.line_divider)!!)
        vDivider.setDrawable(ContextCompat.getDrawable(activity as AppCompatActivity, R.drawable.line_divider)!!)
        view.wishListRv!!.addItemDecoration(hDivider)
        view.wishListRv!!.addItemDecoration(vDivider)

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
                    refreshItems(wishListModelsList.size)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }

        return view
    }

    private fun refreshItems(size: Int) {
        totalItems = size
        view?.wlTotalItems?.text = "$bullet$totalItems Items"
    }

}