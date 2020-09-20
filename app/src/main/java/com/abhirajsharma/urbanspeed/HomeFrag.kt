package com.abhirajsharma.urbanspeed

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhirajsharma.urbanspeed.adapter.HomeAdapter
import com.abhirajsharma.urbanspeed.adapter.HomeCategoryAdapter
import com.abhirajsharma.urbanspeed.model.HomeCategoryModels
import com.abhirajsharma.urbanspeed.model.HomeModel
import com.abhirajsharma.urbanspeed.model.SliderModel
import com.abhirajsharma.urbanspeed.model.dealsofthedayModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFrag : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)




        if (DBquaries.grocery_CartList_product_id.size === 0) {
            DBquaries.loadGroceryCartList(view.context)
        }
        val homeCategoryModelsList: ArrayList<HomeCategoryModels> = ArrayList()
        val homeCategoryAdapter= HomeCategoryAdapter(homeCategoryModelsList)


        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        view.home_category_recycler!!.layoutManager = linearLayoutManager
        view.home_category_recycler!!.adapter = homeCategoryAdapter
        homeCategoryAdapter.notifyDataSetChanged()

        FirebaseFirestore.getInstance().collection("GROCERYCATEGORIES")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")

                        homeCategoryModelsList.add(HomeCategoryModels(document.data.get("image").toString(),
                                document.data.get("title").toString(), document.data.get("tag").toString()))

                    }
                    homeCategoryAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }



        val homeModelList: ArrayList<HomeModel> = ArrayList()
        val homeAdapter = HomeAdapter(homeModelList)
        val grocerymain = LinearLayoutManager(activity)
        grocerymain.orientation = RecyclerView.VERTICAL
        view.home_recycler!!.layoutManager = grocerymain
        view.home_recycler!!.adapter = homeAdapter


        FirebaseFirestore.getInstance().collection("GROCERYHOME").orderBy("index").get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result!!) {
                            if (documentSnapshot["view_type"] as Long == 1L) {
                                val sliderModelList: ArrayList<SliderModel> = ArrayList()
                                val no_of_banners = documentSnapshot["no_of_banners"] as Long
                                for (x in no_of_banners downTo no_of_banners - 2 + 1) {
                                    (sliderModelList).add(SliderModel(documentSnapshot["banner_" + x + "_image"].toString(), documentSnapshot["banner_" + x + "_tag"].toString(), documentSnapshot["banner_" + x + "_background"].toString()))
                                }
                                for (x in 1 until no_of_banners + 1) {
                                    (sliderModelList).add(SliderModel(documentSnapshot["banner_" + x + "_image"].toString(), documentSnapshot["banner_" + x + "_tag"].toString(), documentSnapshot["banner_" + x + "_background"].toString()))
                                }
                                for (x in 1..2) {
                                    (sliderModelList).add(SliderModel(documentSnapshot["banner_" + x + "_image"].toString(), documentSnapshot["banner_" + x + "_tag"].toString(), documentSnapshot["banner_" + x + "_background"].toString()))
                                }
                                homeModelList.add(HomeModel(1, sliderModelList))
                            }
                            if (documentSnapshot["view_type"] as Long == 2L) {
                                val ids = java.util.ArrayList<String>()
                                val  dealsofthedayModelList:ArrayList<dealsofthedayModel> = ArrayList()
                                val no_of_products = documentSnapshot["no_of_products"] as Long
                                val background = documentSnapshot["background_color"].toString()
                                val title = documentSnapshot["title"].toString()
                                for (x in 1 until no_of_products + 1) {
                                   (dealsofthedayModelList).add(dealsofthedayModel(documentSnapshot["image_$x"].toString(), documentSnapshot["name_$x"].toString(), documentSnapshot["description_$x"].toString(),
                                           documentSnapshot["price_$x"].toString(),
                                           documentSnapshot["id_$x"].toString(),
                                           documentSnapshot["tag_$x"].toString()))
                                    ids.add(documentSnapshot["id_$x"].toString())
                                }
                                homeModelList.add(HomeModel(2, title, dealsofthedayModelList, ids, background))
                            }
                            if (documentSnapshot["view_type"] as Long == 3L) {
                                val ids = java.util.ArrayList<String>()
                                var gridList: ArrayList<dealsofthedayModel> = ArrayList()
                                val grid_title = documentSnapshot["title"].toString()
                                val no_of_products = documentSnapshot["no_of_products"] as Long
                                val background = documentSnapshot["background_color"].toString()
                                for (x in 1 until no_of_products + 1) {
                                    gridList.add(dealsofthedayModel(documentSnapshot["image_$x"].toString(), documentSnapshot["name_$x"].toString(), documentSnapshot["description_$x"].toString(), documentSnapshot["price_$x"].toString(),
                                            documentSnapshot["id_$x"].toString(),
                                            documentSnapshot["tag_$x"].toString()))
                                    ids.add(documentSnapshot["id_$x"].toString())
                                }
                                homeModelList.add(HomeModel(3, grid_title, gridList, ids, background))
                            }
                            if (documentSnapshot["view_type"] as Long == 4L) {
                                var circularHorizontallist: ArrayList<HomeCategoryModels> = ArrayList()
                                val no_of_products = documentSnapshot["no_of_products"] as Long
                                for (x in 1 until no_of_products + 1) {
                                    circularHorizontallist.add(HomeCategoryModels(documentSnapshot["image_$x"].toString(), documentSnapshot["name_$x"].toString(), documentSnapshot["tag_$x"].toString()
                                    ))
                                }
                                homeModelList.add(HomeModel(4, 0, "Top Brands", circularHorizontallist))
                            }
                            if (documentSnapshot["view_type"] as Long == 5L) {
                                homeModelList.add(HomeModel(5,
                                        documentSnapshot["name_1"].toString(),
                                        documentSnapshot["name_2"].toString(),
                                        documentSnapshot["name_3"].toString(),
                                        documentSnapshot["name_4"].toString(),
                                        documentSnapshot["image_1"].toString(),
                                        documentSnapshot["image_2"].toString(),
                                        documentSnapshot["image_3"].toString(),
                                        documentSnapshot["image_4"].toString(),
                                        documentSnapshot["title"].toString(),
                                        documentSnapshot["tag_1"].toString(),
                                        documentSnapshot["tag_2"].toString(),
                                        documentSnapshot["tag_3"].toString(),
                                        documentSnapshot["tag_4"].toString()
                                ))
                            }
                        }
                        homeAdapter.notifyDataSetChanged()
                    } else {
                        val error = task.exception!!.message
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }
                }








        return view
    }


}