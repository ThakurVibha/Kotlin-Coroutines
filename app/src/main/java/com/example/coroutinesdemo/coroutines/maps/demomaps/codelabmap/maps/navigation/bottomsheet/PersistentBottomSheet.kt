package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.R.layout.persistent_bottom_sheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.persistent_bottom_sheet.*

class PersistentBottomSheet : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(persistent_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPersistentBottomSheet()
    }

    private fun showPersistentBottomSheet() {
        btnBottomsheet.setOnClickListener {
            Toast.makeText(requireContext(), "HII", Toast.LENGTH_SHORT).show()
        }
    }


}