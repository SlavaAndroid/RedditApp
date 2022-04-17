package com.example.redditapp.ui.details

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.example.redditapp.databinding.FragmentDetailsBinding
import com.example.redditapp.model.PostModel
import com.example.redditapp.ui.list.MyViewModel

class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding
    lateinit var viewModel: MyViewModel

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
        val url = arguments?.getString(ARG_IMAGE_URL)
        context?.let {
            Glide.with(it).load(url).into(binding.ivImage)
        }

        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        binding.saveButton.setOnClickListener {
            val bitmapDrawable = if(binding.ivImage.drawable is GifDrawable){
                (binding.ivImage.drawable as GifDrawable).toBitmap()
            } else {
                (binding.ivImage.drawable as BitmapDrawable).bitmap
            }
            viewModel.saveImage(bitmapDrawable, url, context)
        }

        binding.closeButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}