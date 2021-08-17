package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.bottomnavigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.coroutinesdemo.R
import kotlinx.android.synthetic.main.fragment_bottom_navigation1.*


class BottomNavigationFragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_bottom_navigation1, container, false)
    }

    private fun onClick() {
        btnOpen.setOnClickListener {
            val intent = Intent(requireActivity(), DummyActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            requireActivity().startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Toast.makeText(activity, "Hi i am fragment1", Toast.LENGTH_SHORT).show()
        onClick()


    }


}