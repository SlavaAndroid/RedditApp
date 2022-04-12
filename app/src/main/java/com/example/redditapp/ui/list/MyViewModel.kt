package com.example.redditapp.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.redditapp.model.PostModel

class MyViewModel(application: Application): AndroidViewModel(application) {

    var items = MutableLiveData<MutableList<PostModel>>(mutableListOf())

    init {
        val list = mutableListOf<PostModel>()
        val postModel = PostModel("20", "titleText", "author", "https://www.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png", "https://www.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png",300, 5)
        for (i in 0 until 20) {
            list.add(postModel)
        }
        items.postValue(list)
    }
}