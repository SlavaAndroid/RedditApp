package com.example.redditapp.ui.common

import androidx.fragment.app.Fragment
import com.example.redditapp.R

open class BaseFragment: Fragment() {

    fun addFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}