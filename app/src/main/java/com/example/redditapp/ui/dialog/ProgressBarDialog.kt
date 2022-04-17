package com.example.redditapp.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.redditapp.databinding.FragmentProgressBarDialogBinding

class ProgressBarDialog : DialogFragment() {

    lateinit var binding: FragmentProgressBarDialogBinding

    companion object {
        const val CANCELABLE = "cancelable"
        fun newInstance(isCancelable: Boolean = false): ProgressBarDialog {
            val progressBarDialog = ProgressBarDialog()
            val args = Bundle()
            args.putBoolean(CANCELABLE, isCancelable)
            progressBarDialog.arguments = args
            return progressBarDialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProgressBarDialogBinding.inflate(inflater, container, false)
        isCancelable = arguments?.getBoolean(CANCELABLE) ?: false

        //hide white background
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setStyle(STYLE_NO_FRAME, android.R.style.Theme)
        return binding.root
    }
}