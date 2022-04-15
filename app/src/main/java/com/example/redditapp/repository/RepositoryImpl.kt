package com.example.redditapp.repository

import com.example.redditapp.api.ApiFactory
import com.example.redditapp.model.TopData

open class RepositoryImpl: Repository {

    private var remote = ApiFactory.apiService

    override suspend fun getTopData(): TopData {
        return remote.getTopPosts()
    }
}