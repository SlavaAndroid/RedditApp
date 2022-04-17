package com.example.redditapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redditapp.R
import com.example.redditapp.databinding.ListItemBinding
import com.example.redditapp.model.PostModel
import com.example.redditapp.utils.extensions.getHours

class ItemAdapter(private val onClick: (PostModel) -> Unit) :
    RecyclerView.Adapter<ItemAdapter.ImageViewHolder>() {

    var posts: MutableList<PostModel> = mutableListOf()

    fun addData(list: MutableList<PostModel>) {
        val startIndex = posts.size
        posts.addAll(list)
        notifyItemRangeInserted(startIndex, list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemBinding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class ImageViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val item = binding

        fun bind(model: PostModel) {
            with(model) {
                with(item) {
                    tvPost.text = title
                    tvPostAuthor.text = author
                    tvPostTime.text = postTime.getHours()
                    tvComments.text = numComments.toString()
                    Glide.with(itemView)
                        .load(thumbnail)
                        .centerCrop()
                        .error(R.drawable.ic_outline_outlet_24)
                        .into(ivThumbnail)
                }
            }
            item.ivThumbnail.setOnClickListener { onClick(model) }
        }
    }
}