package me.bohdanov.imagedisplayer.repository.api

import io.reactivex.Single
import me.bohdanov.imagedisplayer.repository.data_models.ApiImageDataModel
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    /**
     * getting list of image items
     * example call: https://picsum.photos/v2/list?page=2&limit=100
     *
     * @param pageNumber number of page to load
     * @param itemsPerPage image items per page (default api value is 30)
     */
    @GET("/v2/list")
    fun getImagesListByPage(
        @Query("page") pageNumber: Int,
        @Query("limit") itemsPerPage: Int
    ): Single<ArrayList<ApiImageDataModel>>
}