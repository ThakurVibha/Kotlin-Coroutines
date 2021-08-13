package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coroutinesdemo.R
import java.util.ArrayList

class RightDrawerFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.right_hand_drawer_fragment, container, false)
    }

}