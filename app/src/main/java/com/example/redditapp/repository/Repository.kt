package com.example.redditapp.repository

import com.example.redditapp.model.TopData

interface Repository {

    suspend fun getTopData(): TopData
}