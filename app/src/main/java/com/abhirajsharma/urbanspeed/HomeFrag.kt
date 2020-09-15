package com.abhirajsharma.urbanspeed

import android.os.Bundle
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
import com.abhirajsharma.urbanspeed.model.dealsofthedayModel
import com.abhirajsharma.urbanspeed.model.sliderModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFrag : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {




        val view = inflater.inflate(R.layout.fragment_home, container, false)



        ////scroll_movement


        ////scroll_movement
//        view.home_scroll!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            val movement = v.scrollY
//            val maxDistance = view.toolbar!!.height
//            val alphaFactor = movement * 1.0f / maxDistance
//            val height = view.appBarLayout!!.scrollY
//            if (height in 0..maxDistance) {
//                /*for image parallax with scroll */
//                /* set visibility */
//                view.search_bar_LL!!.alpha = alphaFactor
//                view.home_search_product_FAB!!.alpha = 1 - alphaFactor
//                if (alphaFactor > 0.8) {
//                    view.appBarLayout!!.setBackgroundColor(Color.parseColor("#252525"))
//                } else {
//                    view.appBarLayout!!.setBackgroundResource(R.drawable.background)
//                }
//            }
//            if (oldScrollY > scrollY) {
//                view.home_search_product_FAB!!.alpha = 1f
//                view.home_search_product_FAB!!.visibility = View.VISIBLE
//                view.appBarLayout!!.setBackgroundResource(R.drawable.background)
//                view.search_bar_LL!!.alpha = 0f
//            } else {
//                view.home_search_product_FAB!!.visibility = View.GONE
//            }
//        })

        ////scroll_movement


        ////scroll_movement
        val ids = ArrayList<String>()

        ///lists


        ///lists

/*  homeCategoryModelsList = ArrayList()
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))
        homeCategoryModelsList.add(HomeCategoryModels("jhbhsda", "helloworld", "kajdsfg"))

        val dealsOfTheDayModelList: ArrayList<dealsofthedayModel> = ArrayList()
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))



        val circularHorizontalList: ArrayList<HomeCategoryModels> = ArrayList()
        circularHorizontalList.add(HomeCategoryModels("image", "title", "afda"))
        circularHorizontalList.add(HomeCategoryModels("image", "title", "afda"))
        circularHorizontalList.add(HomeCategoryModels("image", "title", "afda"))
        circularHorizontalList.add(HomeCategoryModels("image", "title", "afda"))
        circularHorizontalList.add(HomeCategoryModels("image", "title", "afda"))
        circularHorizontalList.add(HomeCategoryModels("image", "title", "afda"))
        circularHorizontalList.add(HomeCategoryModels("image", "title", "afda"))
        circularHorizontalList.add(HomeCategoryModels("image", "title", "afda"))
        circularHorizontalList.add(HomeCategoryModels("image", "title", "afda"))
        circularHorizontalList.add(HomeCategoryModels("image", "title", "afda"))

        homeModelList = ArrayList()
        homeModelList.add(HomeModel(1, sliderModelList))
        homeModelList.add(HomeModel(2, "title", dealsOfTheDayModelList, ids, "#000000"))
        homeModelList.add(HomeModel(3, "grid_title", gridList, ids, "#FFFFFF"))
        homeModelList.add(HomeModel(4, 0, "Top Brands", circularHorizontalList))
        homeModelList.add(HomeModel(5, "name_1", "name_1", "name_1", "name_1", "name_1",
                "name_1", "name_1", "name_1",
                "name_1", "name_1", "name_1", "name_1", "name_1"
        ))
        ///lists
*/
        val homeCategoryModelsList: ArrayList<HomeCategoryModels> = ArrayList()
        val homeCategoryAdapter= HomeCategoryAdapter(homeCategoryModelsList)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        view.home_category_recycler!!.layoutManager = linearLayoutManager
        view.home_category_recycler!!.adapter = homeCategoryAdapter
        homeCategoryAdapter.notifyDataSetChanged()

/*
        FirebaseFirestore.getInstance().collection("GROCERYCATEGORIES").orderBy("index").get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in Objects.requireNonNull(task.result)!!) {
                            homeCategoryModelsList.add(HomeCategoryModels(documentSnapshot["image"].toString(),
                                    documentSnapshot["title"].toString(), documentSnapshot["tag"].toString()))
                        }
                        homeCategoryAdapter.notifyDataSetChanged()
                    }
                }
*/

        val homeModelList: ArrayList<HomeModel> = ArrayList()
        val homeAdapter = HomeAdapter(homeModelList)
        val grocerymain = LinearLayoutManager(activity)
        grocerymain.orientation = RecyclerView.VERTICAL
        view.home_recycler!!.layoutManager = grocerymain
        view.home_recycler!!.adapter = homeAdapter
/*

        FirebaseFirestore.getInstance().collection("GROCERYHOME").orderBy("index").get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result!!) {
                            if (documentSnapshot["view_type"] as Long == 1L) {
                                var sliderModelList: ArrayList<sliderModel> = ArrayList()
                                val no_of_banners = documentSnapshot["no_of_banners"] as Long
                                for (x in no_of_banners downTo no_of_banners - 2 + 1) {
                                    (sliderModelList ).add(sliderModel(documentSnapshot["banner_" + x + "_image"].toString(), documentSnapshot["banner_" + x + "_tag"].toString(), documentSnapshot["banner_" + x + "_background"].toString()))
                                }
                                for (x in 1 until no_of_banners + 1) {
                                    (sliderModelList).add(sliderModel(documentSnapshot["banner_" + x + "_image"].toString(), documentSnapshot["banner_" + x + "_tag"].toString(), documentSnapshot["banner_" + x + "_background"].toString()))
                                }
                                for (x in 1..2) {
                                    (sliderModelList ).add(sliderModel(documentSnapshot["banner_" + x + "_image"].toString(), documentSnapshot["banner_" + x + "_tag"].toString(), documentSnapshot["banner_" + x + "_background"].toString()))
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
*/







        return view
    }


}