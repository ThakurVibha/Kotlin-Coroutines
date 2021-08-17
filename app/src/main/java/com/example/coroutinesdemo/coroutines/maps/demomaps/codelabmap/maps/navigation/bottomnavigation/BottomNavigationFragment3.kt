package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.bottomnavigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.coroutinesdemo.R
import kotlinx.android.synthetic.main.fragment_bottom_navigation3.*



class BottomNavigationFragment3 : Fragment() {
companion object{
    const val FRAGMENT_BACK_STACK="Fragment 3"
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_bottom_navigation3, container, false)

    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvFragment.setText("this is fragment 3")
        Toast.makeText(activity, "Hi i am fragment 3", Toast.LENGTH_SHORT).show()
    }
}