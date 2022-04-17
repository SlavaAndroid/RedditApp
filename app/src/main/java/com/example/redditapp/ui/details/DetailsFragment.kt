package com.example.redditapp.ui.details

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.redditapp.R
import com.example.redditapp.databinding.FragmentDetailsBinding
import com.example.redditapp.model.PostModel
import com.example.redditapp.ui.common.BaseFragment
import com.example.redditapp.ui.common.PERMISSION_REQUEST_CODE
import com.example.redditapp.ui.list.ImageViewModel

class DetailsFragment : BaseFragment() {
    lateinit var binding: FragmentDetailsBinding
    lateinit var viewModel: ImageViewModel

    private var url: String? = null

    companion object {
        private const val ARG_IMAGE_URL = "arg_image_url"
        fun newInstance(model: PostModel): DetailsFragment {
            val detailsFragment = DetailsFragment()
            val args = Bundle()
            args.putString(ARG_IMAGE_URL, model.url)
            detailsFragment.arguments = args
            return detailsFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url = arguments?.getString(ARG_IMAGE_URL)
        context?.let {
            Glide.with(it).load(url).addListener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?, target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(context, e?.message ?: "Error", Toast.LENGTH_SHORT).show()
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?,
                    target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    binding.saveButton.visibility = View.VISIBLE
                    return false
                }
            }).error(R.drawable.ic_outline_outlet_24).into(binding.ivImage)
        }

        viewModel = ViewModelProvider(requireActivity())[ImageViewModel::class.java]

        binding.saveButton.setOnClickListener {
            if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) || Build.VERSION.SDK_INT < 23) {
                saveButton()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        }

        binding.closeButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                saveButton()
            } else {
                Toast.makeText(context, "For save Image need Permission", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveButton() {
        val bitmapDrawable = if (binding.ivImage.drawable is GifDrawable) {
            (binding.ivImage.drawable as GifDrawable).toBitmap()
        } else {
            (binding.ivImage.drawable as BitmapDrawable).bitmap
        }
        viewModel.saveImage(bitmapDrawable, url, context)
    }
}