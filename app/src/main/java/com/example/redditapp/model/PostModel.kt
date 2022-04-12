package com.example.redditapp.model

data class PostModel(
    val id: String?,
    val title: String?,
    val author: String?,
    val thumbnail: String?,
    val imageUrl: String?,
    val numComments: Int?,
    val time: Int
)