package com.example.redditapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redditapp.databinding.FragmentListBinding
import com.example.redditapp.model.PostModel
import com.example.redditapp.ui.common.BaseFragment
import com.example.redditapp.ui.details.DetailsFragment
import com.example.redditapp.utils.extensions.isNetworkAvailable

class ListFragment : BaseFragment() {
    lateinit var binding: FragmentListBinding
    lateinit var viewModel: ImageViewModel

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

        val adapter = ItemAdapter { model ->
            if (model.isImage()) {
                openDetailFragment(model)
            } else {
                Toast.makeText(activity, "NO IMAGE", Toast.LENGTH_SHORT).show()
            }
        }

        val layoutManager = LinearLayoutManager(context)
        binding.rv.layoutManager = layoutManager
        binding.rv.adapter = adapter

        viewModel = ViewModelProvider(requireActivity())[ImageViewModel::class.java]

        if (requireContext().isNetworkAvailable()) {
            viewModel.additionalItems.observe(viewLifecycleOwner, {
                if (it.isNullOrEmpty()) {
                    viewModel.getData()
                } else {
                    adapter.addData(it)
                }
            })
        } else {
            Toast.makeText(activity, "NO INTERNET CONNECTION. TRY LATER", Toast.LENGTH_LONG).show()
        }

        viewModel.isShowingDialog.observe(viewLifecycleOwner, {
            if (it) showDialog()
            else hideDialog()
        })

        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val itemsCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()
                    if ((itemsCount - lastVisible) < COUNT_TO_DOWNLOAD) {
                        viewModel.getData()
                    }
                }
            }
        })
    }

    private fun openDetailFragment(model: PostModel) {
        val detailsFragment = DetailsFragment.newInstance(model)
        if (context.isNetworkAvailable()) {
            replaceFragment(detailsFragment)
        } else {
            Toast.makeText(activity, "NO INTERNET CONNECTION. TRY LATER", Toast.LENGTH_LONG).show()
        }
    }
}



