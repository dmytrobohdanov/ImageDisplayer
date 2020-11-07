package me.bohdanov.imagedisplayer.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.*
import androidx.room.Room
import me.bohdanov.imagedisplayer.repository.api.ApiManager
import me.bohdanov.imagedisplayer.repository.api.data_models.ApiImageDataModel
import me.bohdanov.imagedisplayer.repository.data_base.ImageDbEntity
import me.bohdanov.imagedisplayer.repository.data_base.ImagesDataBase
import me.bohdanov.imagedisplayer.ui.ui_data_models.UiImageDataModel
import me.bohdanov.imagedisplayer.utils.ITEMS_LOADED_PER_PAGE
import java.util.*

class Repository(applicationContext: Context) {
    private val database =
        Room.databaseBuilder(applicationContext, ImagesDataBase::class.java, "imagesDb")
            .build()
    private val apiManager = ApiManager()

    fun getImagesLiveData(): LiveData<PagingData<UiImageDataModel>> {
        return Pager(
            PagingConfig(ITEMS_LOADED_PER_PAGE),
            null,
            ImagesRemoteMediator(
                database = database,
                apiManager = apiManager
            ) { apiImageModelList -> handleApiModelsReceived(apiImageModelList) },
            { database.imagesDao().pagingSource() })
            .liveData
            .map { dbModel ->
                dbModel.map {
                    UiImageDataModel.createFrom(it)
                }
            }
    }

    private fun handleApiModelsReceived(apiImageModelList: ArrayList<ApiImageDataModel>): ArrayList<ImageDbEntity> {
        return ArrayList(apiImageModelList.map { ImageDbEntity.createFrom(it, "") })
    }
}