package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.navgraph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.example.coroutinesdemo.R
import kotlinx.android.synthetic.main.fragment_bottom_navigation1.*
import kotlinx.android.synthetic.main.fragment_bottom_navigation2.*
import kotlinx.android.synthetic.main.fragment_bottom_navigation3.*
import kotlinx.android.synthetic.main.persistent_bottom_sheet.*

class NavgraphActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navgraph)
        onClick()
    }

    private fun onClick() {
        btnOpen.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navFragment1_to_navFragment2);
        }
        btnFragment2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navFragment2_to_navFragment3);
        }
        btnFragment3.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navFragment3_to_navFragment1);
        }
    }
}