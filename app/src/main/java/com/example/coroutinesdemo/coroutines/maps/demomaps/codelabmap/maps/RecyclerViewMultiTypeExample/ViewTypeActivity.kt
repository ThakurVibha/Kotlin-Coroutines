package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.RecyclerViewMultiTypeExample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinesdemo.R
import kotlinx.android.synthetic.main.activity_view_type.*

class ViewTypeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_type)
        setAdapter()
    }

    private fun setAdapter() {
        var dataList = ArrayList<Data>()
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE_1, "Hi! I am in View 1"))
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE2, "Hi! I am in View 2"))
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE3, "Hi! I am in View 3"))
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE_1, "Hi! I am in View 1"))
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE2, "Hi! I am in View 2"))
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE3, "Hi! I am in View 3"))
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE2, "Hi! I am in View 2"))
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE_1, "Hi! I am in View 1"))
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE2, "Hi! I am in View 2"))
        dataList.add(Data(RecyclerViewAdapter.VIEW_TYPE3, "Hi! I am in View 3"))
        var adapter = RecyclerViewAdapter(this, dataList)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adapter
    }
}