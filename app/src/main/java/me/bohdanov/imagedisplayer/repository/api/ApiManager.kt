package me.bohdanov.imagedisplayer.repository.api

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.bohdanov.imagedisplayer.repository.api.api_utils.RetrofitCreator
import me.bohdanov.imagedisplayer.repository.api.data_models.ApiImageDataModel
import me.bohdanov.imagedisplayer.utils.ITEMS_LOADED_PER_PAGE

class ApiManager(
    private val subscribeOnScheduler: Scheduler = Schedulers.io(),
    private val observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()
) {
    private val retrofitObject = RetrofitCreator.getRetrofit()

    /**
     * getting image's objects by page
     *
     * @param pageNumber number of page to load data from
     * @param imagesPerPage number image's items per page, default value is [ITEMS_LOADED_PER_PAGE]
     *
     * @return RxJava [Single] object with list of [ApiImageDataModel]
     */
    fun getImagesByPage(
        pageNumber: Int,
        imagesPerPage: Int = ITEMS_LOADED_PER_PAGE
    ): Single<ArrayList<ApiImageDataModel>> {
        return retrofitObject.getImagesListByPage(pageNumber, imagesPerPage)
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
    }
}