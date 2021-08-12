package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.permissions

import android.content.pm.PackageManager
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.coroutinesdemo.R
import com.google.android.material.snackbar.Snackbar

object Utils {

//    private fun View.showSnackbar(
//        view: View,
//        msg: String,
//        length: Int,
//        actionMessage: CharSequence?,
//        action: (View) -> Unit
//    ) {
//        val snackbar = Snackbar.make(view, msg, length)
//        if (actionMessage != null) {
//            snackbar.setAction(actionMessage) {
//                action(this)
//            }.show()
//        } else {
//            snackbar.show()
//        }
//    }
//
//    fun onClickRequestPermissions(view: View) {
//        when {
//            ContextCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.READ_CONTACTS
//            ) == PackageManager.PERMISSION_GRANTED -> {
////                requestPermissions.launch("image/*")
//                layout.showSnackbar(
//                    view,
//                    getString(R.string.permission_granted),
//                    Snackbar.LENGTH_INDEFINITE,
//                    "These are runtime permissions"
//                ) {}
//            }
//
//            ActivityCompat.shouldShowRequestPermissionRationale(
//                this,
//                android.Manifest.permission.READ_CONTACTS
//            ) -> {
//                layout.showSnackbar(
//                    view,
//                    getString(R.string.permission_required),
//                    Snackbar.LENGTH_INDEFINITE,
//                    getString(R.string.ok)
//                ) {
//                    requestPermissions.launch(
//                        android.Manifest.permission.CAMERA
//                    )
//                }
//            }
//            else -> {
//                requestPermissions.launch(
//                    android.Manifest.permission.CAMERA
//                )
//            }
//        }
//    }

}