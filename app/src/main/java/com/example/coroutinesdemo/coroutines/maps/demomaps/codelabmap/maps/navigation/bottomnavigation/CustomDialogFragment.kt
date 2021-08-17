package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.bottomnavigation

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.coroutinesdemo.R


class CustomDialogFragment : DialogFragment(), View.OnClickListener {

  

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_dialog_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window!!.setBackgroundDrawableResource(R.drawable.custom_dialog_round_corners)
        requireActivity().window.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        onClick()
    }

//    private fun onClick() {
//        val login_button = dialog!!.findViewById(R.id.btnLogin) as Button
//        val next_button=dialog!!.findViewById(R.id.btnNext) as Button
//        login_button.setOnClickListener {
//            Log.e("DialogTag", "onClick: ", )
//            Toast.makeText(requireActivity().applicationContext, "This is next button", Toast.LENGTH_SHORT).show()
//        }
//        next_button.setOnClickListener {
//            Toast.makeText(requireActivity().applicationContext, "This is next button", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onClick(p0: View?) {
        val login_button = dialog!!.findViewById(R.id.btnLogin) as Button
        val next_button = dialog!!.findViewById(R.id.btnNext) as Button
        login_button.setOnClickListener {
            Log.e("DialogTag", "onClick: ")
            Toast.makeText(
                requireActivity().applicationContext,
                "This is next button",
                Toast.LENGTH_SHORT
            ).show()
        }
        next_button.setOnClickListener {
            Toast.makeText(
                requireActivity().applicationContext,
                "This is next button",
                Toast.LENGTH_SHORT
            ).show()
        }
        }



}