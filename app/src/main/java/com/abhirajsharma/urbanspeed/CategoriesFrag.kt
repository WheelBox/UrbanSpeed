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
import com.abhirajsharma.urbanspeed.adapter.CategoriesAdapter
import com.abhirajsharma.urbanspeed.model.HomeCategoryModels
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_categories.view.*

class CategoriesFrag : Fragment() {

    private val TAG = "checkMe"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)

        val categoryModelsList = arrayListOf<HomeCategoryModels>()
        val homeCategoryAdapter = CategoriesAdapter(activity as AppCompatActivity, categoryModelsList)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        view.catRv!!.layoutManager = linearLayoutManager
        view.catRv!!.adapter = homeCategoryAdapter
        homeCategoryAdapter.notifyDataSetChanged()
        FirebaseFirestore.getInstance().collection("GROCERYCATEGORIES")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        categoryModelsList.add(HomeCategoryModels(document.data["image"].toString(),
                                document.data["title"].toString(), document.data["tag"].toString()))
                    }
                    homeCategoryAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }

        return view
    }
}