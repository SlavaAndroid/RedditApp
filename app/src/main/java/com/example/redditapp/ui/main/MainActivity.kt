package com.example.redditapp.ui.main

import android.os.Bundle
import com.example.redditapp.R
import com.example.redditapp.ui.list.ListFragment
import com.example.redditapp.ui.common.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(ListFragment.newInstance())
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount < 2) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}