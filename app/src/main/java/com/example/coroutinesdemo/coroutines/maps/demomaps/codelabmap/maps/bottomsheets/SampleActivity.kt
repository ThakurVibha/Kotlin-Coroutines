package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.bottomsheets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinesdemo.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_sample.*
import java.lang.reflect.Array.newInstance

class SampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        onClick()
    }

    private fun onClick() {
        btnShow.setOnClickListener {
            val bottomsSheetFragment = SheetFragment()
            bottomsSheetFragment.show(supportFragmentManager, "bottomSheetFragment")
            Log.e("TAG", "onClick: ", )
        }
    }
}



