package com.example.redditapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.redditapp.databinding.FragmentDetailsBinding
import com.example.redditapp.model.PostModel

class DetailsFragment: Fragment() {
    lateinit var binding: FragmentDetailsBinding

    companion object {
        private val ARG_IMAGE_URL = "arg_image_url"
        fun newInstance(model: PostModel): DetailsFragment {
            val detailsFragment = DetailsFragment()
            val args = Bundle()
            args.putString(ARG_IMAGE_URL, model.imageUrl)
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
        context?.let { Glide.with(it).load(url).into(binding.ivImage) }

        binding.saveButton.setOnClickListener {

        }
    }

}