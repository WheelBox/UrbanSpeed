package com.abhirajsharma.urbanspeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhirajsharma.urbanspeed.adapter.HomeAdapter
import com.abhirajsharma.urbanspeed.adapter.HomeCategoryAdapter
import com.abhirajsharma.urbanspeed.model.HomeCategoryModels
import com.abhirajsharma.urbanspeed.model.HomeModel
import com.abhirajsharma.urbanspeed.model.dealsofthedayModel
import com.abhirajsharma.urbanspeed.model.sliderModel
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFrag : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        lateinit var homeModelList: List<HomeModel>
        lateinit var homeCategoryAdapter: HomeCategoryAdapter
        lateinit var homeCategoryModelsList: List<HomeCategoryModels>
        lateinit var sliderModelList: List<sliderModel>

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        homeCategoryModelsList = ArrayList()
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
        sliderModelList = ArrayList()
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))
        sliderModelList.add(sliderModel("abdbakl", "hsdfay", "#FFFFFF"))


        val dealsOfTheDayModelList: ArrayList<dealsofthedayModel> = ArrayList()
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        dealsOfTheDayModelList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))


        val gridList: ArrayList<dealsofthedayModel> = ArrayList()
        gridList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        gridList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        gridList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))
        gridList.add(dealsofthedayModel("image", "title", "ahsdbf", "jhfdba", "jfdnsa", "jdfhajd"))

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

        ///lists
        homeCategoryAdapter = HomeCategoryAdapter(homeCategoryModelsList)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        view.home_category_recycler!!.layoutManager = linearLayoutManager
        view.home_category_recycler!!.adapter = homeCategoryAdapter
        homeCategoryAdapter.notifyDataSetChanged()

        val homeAdapter = HomeAdapter(homeModelList)
        val grocerymain = LinearLayoutManager(activity)
        grocerymain.orientation = RecyclerView.VERTICAL
        view.home_recycler!!.layoutManager = grocerymain
        view.home_recycler!!.adapter = homeAdapter


        return view
    }


}