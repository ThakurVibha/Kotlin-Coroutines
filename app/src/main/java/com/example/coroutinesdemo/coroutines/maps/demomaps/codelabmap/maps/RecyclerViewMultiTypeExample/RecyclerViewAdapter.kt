package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.RecyclerViewMultiTypeExample

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinesdemo.R

class RecyclerViewAdapter(context: Context, var list: ArrayList<Data>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val VIEW_TYPE_1 = 1
        val VIEW_TYPE2 = 2
        val VIEW_TYPE3 = 3
    }


    inner class RecyclerViewHolde1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.tvText1)
        var add1: Button = itemView.findViewById(R.id.btnAdd1)

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            message.text = recyclerViewModel.textData
            add1.setOnClickListener {
                message.setText("New String has been added to viewType1")
            }
        }
    }

    inner class RecyclerViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.tvText2)
        var add2: Button = itemView.findViewById(R.id.btnAdd2)

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val recyclerViewModel2 = list[position]
            message.text = recyclerViewModel2.textData
            add2.setOnClickListener {
                message.text = "New String has been added to viewType2"
            }
        }
    }

    inner class RecyclerViewHolder3(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message3: TextView = itemView.findViewById(R.id.tvText3)
        var add3: Button = itemView.findViewById(R.id.btnAdd3)

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val recyclerViewModel3 = list[position]
            message3.text = recyclerViewModel3.textData
            add3.setOnClickListener {
                message3.text = "New String has been added to viewType3"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_1) {
            return RecyclerViewHolde1(
                LayoutInflater.from(parent.context).inflate(R.layout.item_view_1, parent, false)
            )
        } else if (viewType == VIEW_TYPE2) {
            return RecyclerViewHolder2(
                LayoutInflater.from(parent.context).inflate(R.layout.item_view_2, parent, false)
            )
        }
        return RecyclerViewHolder3(
            LayoutInflater.from(parent.context).inflate(R.layout.item_viewtype_3, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].recyclerViewType == VIEW_TYPE_1) {
            (holder as RecyclerViewHolde1).bind(position)
        } else if (list[position].recyclerViewType == VIEW_TYPE2) {
            (holder as RecyclerViewHolder2).bind(position)
        } else {
            (holder as RecyclerViewHolder3).bind(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].recyclerViewType
    }
}