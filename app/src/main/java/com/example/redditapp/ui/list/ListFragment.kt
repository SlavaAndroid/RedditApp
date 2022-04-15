package com.example.redditapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redditapp.databinding.FragmentListBinding
import com.example.redditapp.model.PostModel
import com.example.redditapp.ui.common.BaseFragment
import com.example.redditapp.ui.details.DetailsFragment

class ListFragment: BaseFragment() {
    lateinit var binding: FragmentListBinding
    lateinit var viewModel: MyViewModel

    companion object {
        fun newInstance(): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MyAdapter { model ->
            openDetailFragment(model)
        }

        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = adapter

        viewModel = ViewModelProvider(requireActivity()) [MyViewModel::class.java]

        viewModel.items.observe(viewLifecycleOwner, {
            adapter.posts = it
        })
        viewModel.getData()
    }

    private fun openDetailFragment(model: PostModel) {
        val detailsFragment = DetailsFragment.newInstance(model)
        addFragment(detailsFragment)
    }
}



