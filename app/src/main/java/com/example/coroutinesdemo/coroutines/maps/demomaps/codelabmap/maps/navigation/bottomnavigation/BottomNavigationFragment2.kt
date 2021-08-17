package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.bottomnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.coroutinesdemo.R


class BottomNavigationFragment2 : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().supportFragmentManager.beginTransaction().addToBackStack(
            BottomNavigationFragment3.FRAGMENT_BACK_STACK
        )
        return inflater.inflate(R.layout.fragment_bottom_navigation2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Toast.makeText(activity, "Hi i am fragment 3", Toast.LENGTH_SHORT).show()
    }
}