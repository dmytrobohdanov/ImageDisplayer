package me.bohdanov.imagedisplayer.ui.ui_data_models

import androidx.recyclerview.widget.DiffUtil
import me.bohdanov.imagedisplayer.repository.data_base.ImageDbEntity

data class UiImageDataModel(
    val id: String,
    val author: String,
    val maxWidth: Int,
    val maxHeight: Int,
    val localFileUriString: String,
    val webUrl: String
) {
    companion object {
        fun createFrom(dbEntity: ImageDbEntity): UiImageDataModel {
            return UiImageDataModel(
                id = dbEntity.id,
                author = dbEntity.author,
                maxWidth = dbEntity.maxWidth,
                maxHeight = dbEntity.maxHeight,
                localFileUriString = dbEntity.localFileUriString,
                webUrl = dbEntity.webUrl
            )
        }

        fun getDiffCallback(): DiffUtil.ItemCallback<UiImageDataModel> {
            return object : DiffUtil.ItemCallback<UiImageDataModel>() {
                override fun areItemsTheSame(
                    oldUiImageDataModel: UiImageDataModel,
                    newUiImageDataModel: UiImageDataModel
                ) = oldUiImageDataModel.id == newUiImageDataModel.id

                override fun areContentsTheSame(
                    oldUiImageDataModel: UiImageDataModel,
                    newUiImageDataModel: UiImageDataModel
                ) = oldUiImageDataModel == newUiImageDataModel
            }
        }
    }
}