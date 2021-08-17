package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.navigation.tablayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerFragmentAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm,
) {

    private val fragmentList=ArrayList<Fragment>()
    override fun getCount(): Int {
        return fragmentList.size
    }


    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }
    fun addFragments(fragment: Fragment){
        fragmentList.add(fragment)
    }
}