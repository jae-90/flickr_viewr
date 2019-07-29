package com.jw.flickrviewr.favorites

import android.app.Application
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jw.flickrviewr.data.Photo
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

/**
 * ViewModel for the Favorites screen.
 */
class FavoritesViewModel(app: Application) : AndroidViewModel(app) {

    val savedPhotos: MutableLiveData<List<Photo>> by lazy { MutableLiveData<List<Photo>>() }

    init {
        refreshSavedPhotoList()
    }

    private fun refreshSavedPhotoList() {
        val list = getListOfSavedPhotos()
        savedPhotos.value?.let {
            if (it == list)
                return
            else
                savedPhotos.postValue(getListOfSavedPhotos())
        }
    }

    private fun getListOfSavedPhotos(): List<Photo> {
        val fileList = ArrayList<Photo>()
        viewModelScope.launch {
            val path = getApplication<Application>().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
            val directory = File(path)
            val files = directory.listFiles()

            for (file in files!!) {
                val photo = Photo(id = file.name, url = "$path/${file.name}")
                fileList.add(photo)
            }
        }
        if (savedPhotos.value != fileList)
            savedPhotos.postValue(fileList)

        return fileList
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

}