package com.abhirajsharma.urbanspeed.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abhirajsharma.urbanspeed.R
import com.abhirajsharma.urbanspeed.model.dealsofthedayModel
import com.bumptech.glide.Glide

class TodayOffersAdapter(val context: Context, val homeModelList: ArrayList<dealsofthedayModel>)
    : RecyclerView.Adapter<TodayOffersAdapter.OffersViewHolder>() {

    class OffersViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val itemImg: ImageView = item.findViewById(R.id.dealsofthedayitemimage)
        val itemName: TextView = item.findViewById(R.id.dealsofthedayitemname)
        val itemDes: TextView = item.findViewById(R.id.dealsofthedayitedescription)
        val itemPrice: TextView = item.findViewById(R.id.dealsofthedayitemprice)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.dealsoftheday_recycler_item, parent, false)
        return OffersViewHolder(view)
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {
        holder.itemName.text = homeModelList[position].title
        holder.itemDes.text = homeModelList[position].description
        holder.itemPrice.text = homeModelList[position].price
        Glide.with(context).load(homeModelList[position].image).into(holder.itemImg)
    }

    override fun getItemCount(): Int = homeModelList.size
}