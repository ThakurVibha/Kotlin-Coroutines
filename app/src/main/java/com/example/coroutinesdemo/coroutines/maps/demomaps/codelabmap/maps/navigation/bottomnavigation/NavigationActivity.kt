package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.bottomnavigation

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ListAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.toast
import kotlinx.android.synthetic.main.activity_navigation.*


class NavigationActivity : AppCompatActivity() {
    lateinit var adapter: ListAdapter
    companion object {
        private const val FRAGMENT_BACK_STACK = "myfragment"
    }
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.nav_open,
            R.string.nav_close
        )
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.nav_open,
            R.string.nav_close
        )
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setHomeButtonEnabled(true)
        toggle.syncState()
        onNavigationSelection()
        onBottomNavigationSelected()
    }

    private fun onBottomNavigationSelected() {
        bottom_navigation.setOnItemSelectedListener {
            Log.e("//", "onCreate: ")
            when (it.itemId) {
                R.id.fragment1 -> {
                    "this is toast".toast(this)
                    setCurrentFragment(BottomNavigationFragment1())
                }

                R.id.fragment2 -> setCurrentFragment(BottomNavigationFragment2())
                R.id.fragment3 -> setCurrentFragment(BottomNavigationFragment3())
            }
            true
        }
    }

    private fun onNavigationSelection() {
        nav_view1.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mapSatellite ->
                    Toast.makeText(applicationContext, "this is satellite", Toast.LENGTH_SHORT)
                        .show()
            }
            true
        }

        nav_view2.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mapSatellite ->
                    Toast.makeText(
                        applicationContext,
                        "I am also working fine ",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                R.id.mapNormal ->
                    Toast.makeText(this, "I am working fine ", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        Log.e("TAG", "setCurrentFragment: ")
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).addToBackStack(null)
            .commitAllowingStateLoss()
    }
}