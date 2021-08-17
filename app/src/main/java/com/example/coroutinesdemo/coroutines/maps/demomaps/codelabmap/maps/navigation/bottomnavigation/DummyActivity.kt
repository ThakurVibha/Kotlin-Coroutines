package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.bottomnavigation

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinesdemo.R
import kotlinx.android.synthetic.main.activity_dummy.*


class DummyActivity : AppCompatActivity() {
    private var fragment3 = BottomNavigationFragment3()

    companion object {
        const val FRAGMENT_STRING_TAG = "My Fragment"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportFragmentManager.beginTransaction().add(fragment3, FRAGMENT_STRING_TAG)
//            .addToBackStack(null).commit()
        onClick()
    }

    private fun onClick() {
        btnClick.setOnClickListener{
            Toast.makeText(this, "ttt", Toast.LENGTH_SHORT).show()
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                finish()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }


}