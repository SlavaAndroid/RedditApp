package com.example.redditapp.ui.list

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.redditapp.model.PostModel
import com.example.redditapp.repository.Repository
import com.example.redditapp.repository.RepositoryImpl
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class MyViewModel(application: Application) : AndroidViewModel(application) {

    var items = MutableLiveData<MutableList<PostModel>>(mutableListOf())
    private val repository: Repository = RepositoryImpl()

//    var isLoading = MutableLiveData<Boolean>()

    fun getData() {
        viewModelScope.launch {
            val response = repository.getTopData()
            val after = response.after
            val listData = response.childrenData.postModelData.map { it.postModel }
            items.postValue(listData as MutableList<PostModel>)
//            downloadFinished()
        }
    }

//    private fun downloadFinished() {
//        isLoading.value = true
//    }

    fun saveImage(bitmap: Bitmap?, url: String?, context: Context?) {
        viewModelScope.launch {

            //Generating a file name
            val filename = "${System.currentTimeMillis()}.${url?.substringAfterLast(".")?:"jpg"}"

            //Output stream
            var fos: OutputStream? = null

            //For devices running android >= Q
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //getting the contentResolver
                context?.contentResolver?.also { resolver ->

                    //Content resolver will process the contentvalues
                    val contentValues = ContentValues().apply {

                        //putting file information in content values
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, url)
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }

                    //Inserting the contentValues to contentResolver and getting the Uri
                    val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    //Opening an outputstream with the Uri that we got
                    fos = imageUri?.let { resolver.openOutputStream(it) }
                }
            } else {
                //These for devices running on android < Q
                //So I don't think an explanation is needed here
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val image = File(imagesDir, filename)
                fos = FileOutputStream(image)
            }

            fos?.use {
                //Finally writing the bitmap to the output stream that we opened
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
                Toast.makeText(context, "Saved to Images", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
