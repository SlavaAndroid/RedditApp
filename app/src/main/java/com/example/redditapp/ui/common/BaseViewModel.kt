package com.example.redditapp.ui.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val isShowingDialog = MutableLiveData<Boolean>(false)

    protected fun showDialog(){
        isShowingDialog.postValue(true)
    }

    protected fun hideDialog(){
        isShowingDialog.postValue(false)
    }

}
