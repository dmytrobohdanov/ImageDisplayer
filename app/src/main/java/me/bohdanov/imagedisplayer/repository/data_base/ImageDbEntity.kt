package me.bohdanov.imagedisplayer.repository.data_base

import android.net.Uri
import androidx.room.Entity
import me.bohdanov.imagedisplayer.repository.api.data_models.ApiImageDataModel

@Entity(tableName = "images")
data class ImageDbEntity(
    val id: String,
    val author: String,
    val maxWidth: Int,
    val maxHeight: Int,
    val localFileUri: Uri,
    val webUrl: String
) {
    companion object {
        fun createFrom(apiImageModel: ApiImageDataModel, localFileUri: Uri): ImageDbEntity {
            return ImageDbEntity(
                id = apiImageModel.id,
                author = apiImageModel.author,
                maxWidth = apiImageModel.width,
                maxHeight = apiImageModel.height,
                localFileUri = localFileUri,
                webUrl = apiImageModel.download_url
            )
        }
    }
}