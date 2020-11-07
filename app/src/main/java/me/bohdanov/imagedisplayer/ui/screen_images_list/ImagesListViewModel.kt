package me.bohdanov.imagedisplayer.ui.screen_images_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import me.bohdanov.imagedisplayer.utils.App

class ImagesListViewModel(application: Application) : AndroidViewModel(application) {
    var imagesPagedList = getApplication<App>().getRepository().getImagesLiveData()

}