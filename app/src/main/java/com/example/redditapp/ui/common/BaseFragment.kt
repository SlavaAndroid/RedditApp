package com.example.redditapp.ui.common

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.redditapp.R
import com.example.redditapp.ui.dialog.ProgressBarDialog

const val PERMISSION_REQUEST_CODE = 1122

open class BaseFragment : Fragment() {

    private var dialog: DialogFragment? = null

    fun addFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        parentFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .apply {
                if (addToBackStack)
                    addToBackStack(null)
            }
            .addToBackStack(null)
            .commit()
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .apply {
                if (addToBackStack)
                    addToBackStack(null)
            }
            .addToBackStack(null)
            .commit()
    }

    fun showDialog() {
        dialog?.dismiss()
        dialog = ProgressBarDialog.newInstance()
        dialog?.show(parentFragmentManager, "")
    }

    fun hideDialog() {
        dialog?.dismiss()
    }

    fun checkPermission(permission: String) =
        checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED

}