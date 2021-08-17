package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.tablayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinesdemo.R
import kotlinx.android.synthetic.main.viewpager_item.view.*

class ViewPagerAdapter(private val images: List<Int>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        var myImages = images[position]
        holder.itemView.ivImage.setImageResource(myImages)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}