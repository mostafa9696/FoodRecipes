package com.example.mostafahussien.practicekotlin2.UI.Home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.mostafahussien.practicekotlin2.UI.likes.LikesFragment
import com.example.mostafahussien.practicekotlin2.UI.recipes.RecipesFragment
import com.example.mostafahussien.practicekotlin2.UI.search.SearchFragment

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */

class ViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment? {
        return when (position){
            0 -> RecipesFragment.newInstance()
            1 -> LikesFragment.newInstance()
            2 -> SearchFragment.newInstance()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }
}