package com.example.redditapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redditapp.databinding.ListItemBinding
import com.example.redditapp.model.PostModel

class MyAdapter(private val onClick: (PostModel) -> Unit): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var posts: MutableList<PostModel> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class MyViewHolder(binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val item = binding

        fun bind(model: PostModel) {
            with(model) {
                with(item) {
                    tvPost.text = title
                    tvPostAuthor.text = author
                    tvPostTime.text = time.toString()
                    tvComments.text = numComments.toString()
                    Glide.with(itemView)
                        .load(thumbnail)
                        .centerCrop()
                        .into(ivThumbnail)
                }
            }
            item.ivThumbnail.setOnClickListener { onClick(model) }
        }
    }
}