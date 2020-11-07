package me.bohdanov.imagedisplayer.repository.api.data_models

data class ApiImageDataModel(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String
)