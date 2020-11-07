package me.bohdanov.imagedisplayer.repository.api

import io.reactivex.Single
import me.bohdanov.imagedisplayer.repository.data_models.ApiImageDataModel
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    //    "https://picsum.photos/v2/list?page=2&limit=100"
    @GET("/v2/list")
    suspend fun getImagesListByPage(
        @Query("page") pageNumber: Int,
        @Query("limit") itemsPerPage: Int
    ): Single<ArrayList<ApiImageDataModel>>
}