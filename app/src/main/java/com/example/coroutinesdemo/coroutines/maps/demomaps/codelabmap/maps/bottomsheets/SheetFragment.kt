package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.bottomsheets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.coroutinesdemo.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_bottom_sheet.*

class SheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetElements()
    }

    private fun setBottomSheetElements() {
        textView1.setOnClickListener {
            Toast.makeText(activity, "Location 1 has been selected", Toast.LENGTH_LONG).show()
        }
        textView2.setOnClickListener {
            Toast.makeText(activity, "Location 2 has been selected", Toast.LENGTH_LONG).show()
        }
        textView3.setOnClickListener {
            Toast.makeText(activity, "Location 3 has been selected", Toast.LENGTH_LONG).show()
        }
    }
}