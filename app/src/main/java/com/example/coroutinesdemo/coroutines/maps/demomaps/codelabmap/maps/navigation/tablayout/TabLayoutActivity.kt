package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.tablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.coroutinesdemo.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_tab_layout.*
import kotlinx.android.synthetic.main.activity_toolbar.*

class TabLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout)
        setAdapter()
        attachViewPager()
    }


    private fun attachViewPager() {
        TabLayoutMediator(myTabLayout, myViewPager) { tab, position ->
            tab.text = "Tab ${position + 1}"
            tab.contentDescription = "This is tablayout "
        }.attach()

    }
    private fun setAdapter() {
        var myImages = listOf(
            R.drawable.ic_baseline_remove_red_eye_24,
            R.drawable.ic_baseline_location_on_24,
            R.drawable.ic_baseline_run_circle_24
        )
        val adapter = ViewPagerAdapter(myImages)
        myViewPager.adapter = adapter
        myViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        myViewPager.isFakeDragging
    }


}