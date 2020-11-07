package me.bohdanov.imagedisplayer.repository.data_base

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.bohdanov.imagedisplayer.repository.api.data_models.ApiImageDataModel

@Entity(tableName = "images")
data class ImageDbEntity(
    @PrimaryKey
    val id: String,
    val author: String,
    val maxWidth: Int,
    val maxHeight: Int,
    val localFileUriString: String,
    val webUrl: String
) {
    companion object {
        fun createFrom(
            apiImageModel: ApiImageDataModel,
            localFileUriString: String
        ): ImageDbEntity {
            return ImageDbEntity(
                id = apiImageModel.id,
                author = apiImageModel.author,
                maxWidth = apiImageModel.width,
                maxHeight = apiImageModel.height,
                localFileUriString = localFileUriString,
                webUrl = apiImageModel.download_url
            )
        }
    }
}