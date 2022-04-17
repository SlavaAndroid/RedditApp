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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.redditapp.model.PostModel
import com.example.redditapp.repository.Repository
import com.example.redditapp.repository.RepositoryImpl
import com.example.redditapp.ui.common.BaseViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

const val COUNT_TO_DOWNLOAD = 3

class ImageViewModel(application: Application) : BaseViewModel(application) {

    var additionalItems = MutableLiveData<MutableList<PostModel>>(mutableListOf())

    private val repository: Repository = RepositoryImpl()
    private var after: String? = null
    private var isLoading = false

    fun getData() {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            try {
                showDialog()
                val response = repository.getTopData(after)
                after = response.childrenData.after
                val listData = response.childrenData.postModelData.map { it.postModel }
                additionalItems.postValue(listData as MutableList<PostModel>)
            } catch (error: Throwable) {
                val context = getApplication<Application>().applicationContext
                Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
            } finally {
                isLoading = false
                hideDialog()
            }
        }
    }

    fun saveImage(bitmap: Bitmap?, url: String?, context: Context?) {
        viewModelScope.launch {
            try {
                showDialog()
                //Generating a file name
                val filename = "${System.currentTimeMillis()}.${
                    url?.substringAfterLast(".")
                        ?: "jpg"
                }"

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
                            put(
                                MediaStore.MediaColumns.RELATIVE_PATH,
                                Environment.DIRECTORY_PICTURES
                            )
                        }

                        //Inserting the contentValues to contentResolver and getting the Uri
                        val imageUri: Uri? =
                            resolver.insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                contentValues
                            )

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
            } catch (error: Throwable) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            } finally {
                hideDialog()
            }
        }
    }
}
