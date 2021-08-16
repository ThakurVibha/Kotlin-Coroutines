package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinesdemo.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_toolbar.*


class ToolbarActivity : AppCompatActivity() {
    companion object {
        private const val CUSTOM_DIALOG = "My custom dialog "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
        onClick()
        showPersistentBottomSheet()
    }

    private fun showPersistentBottomSheet() {
        val persistentBottomSheet = PersistentBottomSheet()
//        val bottomSheet: View = coordinatorLayout.findViewById(R.id.perBottomSheet)

//        var bottomSheetBehavior=BottomSheetBehavior.from(bottomSheet)
//        bottomSheetBehavior.state(bottomSheetBehavior)
        persistentBottomSheet.show(supportFragmentManager, "bottomSheetFragment")
        Log.e("TAG", "onClick: ")
    }

    private fun onClick() {
        ivStar.setOnClickListener {
            Toast.makeText(this, "Star has been clicked", Toast.LENGTH_SHORT).show()
        }
        ivSearch.setOnClickListener {
            Toast.makeText(this, "Search as per your wish", Toast.LENGTH_SHORT).show()
            CustomDialogFragment().show(supportFragmentManager, CUSTOM_DIALOG)
        }
        btnBottomSheet.setOnClickListener {
            val bottomsSheetFragment = SheetFragment()
            bottomsSheetFragment.show(supportFragmentManager, "bottomSheetFragment")
            Log.e("TAG", "onClick: ")
        }
        ivStar.setOnClickListener {
            Toast.makeText(this, "action from toolbar", Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("ResourceAsColor", "ShowToast")
    fun showSnackbar(view: View) {
        val snackbar =
            Snackbar.make(
                view,
                "This is demo application to practice android Topics",
                Snackbar.LENGTH_SHORT
            )
                .setAction("Perform Action", View.OnClickListener {
                    Toast.makeText(applicationContext, "heyy", Toast.LENGTH_LONG).show()
                    var intent = Intent(applicationContext, NavigationActivity::class.java)
                    startActivity(intent)
                })
                .setAnchorView(view)
        snackbar.show()
    }
}