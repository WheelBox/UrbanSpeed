package com.abhirajsharma.urbanspeed.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhirajsharma.urbanspeed.R
import com.abhirajsharma.urbanspeed.model.HomeCategoryModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.categories_item.view.*

class CategoriesAdapter(private val context: Context, private val list: ArrayList<HomeCategoryModels>) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    class CategoriesViewHolder(item: View) : RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.categories_item, parent, false)
        return CategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.itemView.catName.text = list[position].title
        Glide.with(context).load(list[position].imageResource.toString()).into(holder.itemView.catImg)
    }

    override fun getItemCount(): Int = list.size
}