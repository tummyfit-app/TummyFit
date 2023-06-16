package com.capstoneproject.tummyfit.ui.detailmeal.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstoneproject.tummyfit.ui.detailmeal.IngredientFragment
import com.capstoneproject.tummyfit.ui.detailmeal.InstructionsFragment

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class SectionsPagerAdapter(
    fragment: Fragment,
    bundle: Bundle
) : FragmentStateAdapter(fragment) {

    private var fragmentBundle: Bundle = bundle

    init {
        fragmentBundle = bundle
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(
        position: Int
    ): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = IngredientFragment()
            1 -> fragment = InstructionsFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
}