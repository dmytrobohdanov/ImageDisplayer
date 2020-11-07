package me.bohdanov.imagedisplayer.repository.data_base

import androidx.room.Entity
import me.bohdanov.imagedisplayer.repository.api.data_models.ApiImageDataModel

@Entity(tableName = "images")
data class ImageDbEntity(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String
) {
    companion object {
        fun createFrom(apiImageModel: ApiImageDataModel): ImageDbEntity {
            return ImageDbEntity(
                id = apiImageModel.id,
                author = apiImageModel.author,
                width = apiImageModel.width,
                height = apiImageModel.height,
                url = apiImageModel.url,
                download_url = apiImageModel.download_url
            )
        }
    }
}