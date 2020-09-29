package com.abhirajsharma.urbanspeed

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhirajsharma.urbanspeed.model.GroceryProductModel
import com.abhirajsharma.urbanspeed.others.GlobalInfo
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFrag : Fragment() {

    lateinit var rootRef: FirebaseFirestore
    private var adapter: FirestoreRecyclerAdapter<GroceryProductModel, UsersViewHolder>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        Log.d("checkMe", "From search Frag" + GlobalInfo.searchFragment.toString())

        val textQuery = GlobalInfo.textSearch
//        firebaseUserSearch(textQuery)

        view.searchCategoriesRv.layoutManager = LinearLayoutManager(activity)

        rootRef = FirebaseFirestore.getInstance()


        val query = rootRef.collection("PRODUCTS").orderBy("name", Query.Direction.ASCENDING).startAt(textQuery)
                .endAt(textQuery + "\uf8ff")

        val options: FirestoreRecyclerOptions<GroceryProductModel> = FirestoreRecyclerOptions.Builder<GroceryProductModel>()
                .setQuery(query, GroceryProductModel::class.java)
                .build()

        adapter = object : FirestoreRecyclerAdapter<GroceryProductModel, UsersViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.categories_search_item, parent, false)
                return UsersViewHolder(activity as AppCompatActivity, view)
            }

            override fun onBindViewHolder(holder: UsersViewHolder, position: Int, model: GroceryProductModel) {
                holder.setProductName(model.name, model.description, model.image)
            }

        }
        view.searchCategoriesRv.adapter = adapter
        return view
    }

    private class UsersViewHolder(private val context: Context, private val view: View) : RecyclerView.ViewHolder(view) {
        fun setProductName(productName: String, productDes: String, imgItem: String?) {
            val textView = view.findViewById<TextView>(R.id.searchItemName)
            val textView2 = view.findViewById<TextView>(R.id.searchItemDes)
//            val img = view.findViewById<ImageView>(R.id.searchItemImg)
            textView.text = productName
            textView2.text = productDes
//            Glide.with(context).load(imgItem).into(img)

        }
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null) {
            adapter!!.stopListening()
        }
    }


}