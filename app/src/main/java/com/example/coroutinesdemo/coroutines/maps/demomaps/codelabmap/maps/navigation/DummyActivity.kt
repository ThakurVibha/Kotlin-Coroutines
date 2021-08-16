package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.coroutinesdemo.R
import kotlinx.android.synthetic.main.activity_navigation.*


class DummyActivity : AppCompatActivity() {
    private var fragment3=BottomNavigationFragment3()
    companion object {
        const val FRAGMENT_STRING_TAG = "My Fragment"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction().add(fragment3, FRAGMENT_STRING_TAG).addToBackStack(null).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}