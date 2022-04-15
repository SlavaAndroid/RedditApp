package com.example.redditapp.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.redditapp.model.PostModel
import com.example.redditapp.repository.Repository
import com.example.redditapp.repository.RepositoryImpl
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {

    var items = MutableLiveData<MutableList<PostModel>>(mutableListOf())
    private val repository: Repository = RepositoryImpl()

    fun getData() {
        viewModelScope.launch {
            val response = repository.getTopData()
            val after = response.after
            val listData = response.childrenData.postModelData.map { it.postModel }
            items.postValue(listData as MutableList<PostModel>)
        }
    }
}