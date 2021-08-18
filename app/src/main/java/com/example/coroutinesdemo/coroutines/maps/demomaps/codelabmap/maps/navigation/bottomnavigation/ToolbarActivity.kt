package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.bottomnavigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.bottomsheet.PersistentBottomSheet
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.bottomsheet.SheetFragment
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.tablayout.ViewPagerFragmentAdapter
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.android.synthetic.main.activity_toolbar.tabLayout

class ToolbarActivity : AppCompatActivity() {
    companion object {
        private const val CUSTOM_DIALOG = "My custom dialog "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
//

    }

//
//    private fun setsUpTabs() {
//        var viewPagerAdapter = ViewPagerFragmentAdapter(supportFragmentManager)
//        viewPagerAdapter.addFragments(BottomNavigationFragment3())
//        viewPagerAdapter.addFragments(BottomNavigationFragment2())
//        viewPagerAdapter.addFragments(BottomNavigationFragment1())
//        viewPager.adapter = viewPagerAdapter
//        tabLayout.setupWithViewPager(viewPager)
//        customizeTabs()
//
//    }
//
//    private fun customizeTabs() {
//        tabLayout.getTabAt(0)!!.text = "TAB 1"
//        tabLayout.getTabAt(1)!!.text = "TAB 2"
//        tabLayout.getTabAt(2)!!.text = "TAB 3"
//        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_run_circle_24)
//        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_location_on_24)
//        tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_run_circle_24)
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                "This is toast".toast(this@ToolbarActivity)
//            }
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                Toast.makeText(this@ToolbarActivity, "HIII", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                Toast.makeText(this@ToolbarActivity, "HIII", Toast.LENGTH_SHORT).show()
//            }
//
//        })
//    }
//
//
//    private fun showPersistentBottomSheet() {
//        val persistentBottomSheet = PersistentBottomSheet()
//        persistentBottomSheet.show(supportFragmentManager, "bottomSheetFragment")
//        Log.e("TAG", "onClick: ")
//    }
//
//    private fun onClick() {
//        ivStar.setOnClickListener {
//            "This is toast".toast(this)
//        }
//        ivSearch.setOnClickListener {
//            Toast.makeText(applicationContext, "Search as per your wish", Toast.LENGTH_SHORT).show()
//            CustomDialogFragment().show(supportFragmentManager, CUSTOM_DIALOG)
////            "This is toast".toast(this)
//
//        }
//        btnBottomSheet.setOnClickListener {
//            val bottomsSheetFragment = SheetFragment()
//            bottomsSheetFragment.show(supportFragmentManager, "bottomSheetFragment")
//            Log.e("TAG", "onClick: ")
//        }
//        ivStar.setOnClickListener {
//            Toast.makeText(this, "action from toolbar", Toast.LENGTH_SHORT).show()
//        }
//
//    }
//
//    @SuppressLint("ResourceAsColor", "ShowToast")
//    fun showSnackBar(view: View) {
//        val snackbar =
//            Snackbar.make(
//                view,
//                "This is demo application to practice android Topics",
//                Snackbar.LENGTH_SHORT
//            )
//                .setAction("Perform Action", View.OnClickListener {
//                    Toast.makeText(applicationContext, "heyy", Toast.LENGTH_LONG).show()
//                    var intent = Intent(applicationContext, NavigationActivity::class.java)
//                    startActivity(intent)
//                })
//                .setAnchorView(view)
//        snackbar.show()
//    }
//

}

