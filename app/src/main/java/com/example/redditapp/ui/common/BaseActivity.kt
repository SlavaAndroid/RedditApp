package com.example.redditapp.ui.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.redditapp.R

open class BaseActivity: AppCompatActivity() {

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .apply {
                if (addToBackStack)
                    addToBackStack(null)
            }
            .commit()
    }

    fun addFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .apply {
                if (addToBackStack)
                    addToBackStack(null)
            }
            .commit()
    }
}