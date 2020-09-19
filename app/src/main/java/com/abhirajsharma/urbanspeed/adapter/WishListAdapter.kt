package com.abhirajsharma.urbanspeed.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.abhirajsharma.urbanspeed.R
import com.abhirajsharma.urbanspeed.model.WishListModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.wish_list_item.view.*

class WishListAdapter(private val context: Context, private val list: ArrayList<WishListModel>)
    : RecyclerView.Adapter<WishListAdapter.WishListViewHolder>() {

    class WishListViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val star1: ImageView = item.findViewById(R.id.myOrder_star1)
        val star2: ImageView = item.findViewById(R.id.myOrder_star2)
        val star3: ImageView = item.findViewById(R.id.myOrder_star3)
        val star4: ImageView = item.findViewById(R.id.myOrder_star4)
        val star5: ImageView = item.findViewById(R.id.myOrder_star5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.wish_list_item, parent, false)
        return WishListViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        val current = list[position]
        holder.itemView.wishListName.text = current.wishName
        holder.itemView.wishListPrice.text = "₹" + current.price
        holder.itemView.wishListStock.text = current.inStock
        Glide.with(context).load(current.img).into(holder.itemView.wishListImg)
        Glide.with(context).load(current.logo).into(holder.itemView.wishListItemLogo)
        val starRating = arrayListOf<ImageView>()
        starRating.add(holder.star1)
        starRating.add(holder.star2)
        starRating.add(holder.star3)
        starRating.add(holder.star4)
        starRating.add(holder.star5)
        val rating = current.rating
        for (x in 0 until rating) {
            starRating[x].setColorFilter(Color.YELLOW)
        }
    }


    override fun getItemCount(): Int = list.size
}