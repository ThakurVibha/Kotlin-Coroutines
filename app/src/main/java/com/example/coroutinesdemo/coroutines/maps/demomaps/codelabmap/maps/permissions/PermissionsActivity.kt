package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.permissions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.databinding.ActivityPermissionsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_permissions.*
import java.util.jar.Manifest

class PermissionsActivity : AppCompatActivity() {
    lateinit var layout: View
    lateinit var binding: ActivityPermissionsBinding


    private val imagePickerContarct =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            ivResult.setImageURI(it)
        }

    @SuppressLint("SetTextI18n")
    private val resultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                tvTag.text = "we have got the Result"
            } else {
                tvTag.text = "Failed to get the Result"
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionsBinding.inflate(layoutInflater)
        val view = binding.root
        layout = binding.btnStart
        layout = binding.tvTag
        setContentView(view)
        onClick()
    }

    private fun onClick() {
        btnStart.setOnClickListener {
            var intent = Intent(applicationContext, ActivityResult::class.java)
            resultContract.launch(intent)
        }
        btnImagePicker.setOnClickListener {
            imagePickerContarct.launch("image/*")
        }
    }

}