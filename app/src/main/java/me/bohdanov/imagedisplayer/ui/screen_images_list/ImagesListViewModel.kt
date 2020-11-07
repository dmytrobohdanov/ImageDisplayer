package me.bohdanov.imagedisplayer.ui.screen_images_list

import androidx.lifecycle.AndroidViewModel
import me.bohdanov.imagedisplayer.utils.App

class ImagesListViewModel(application: App) : AndroidViewModel(application) {
    var imagesPagedList = getApplication<App>().getRepository().getImagesLiveData()

}