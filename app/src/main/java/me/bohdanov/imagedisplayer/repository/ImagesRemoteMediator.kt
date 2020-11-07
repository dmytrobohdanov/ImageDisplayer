package me.bohdanov.imagedisplayer.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.bohdanov.imagedisplayer.repository.api.ApiManager
import me.bohdanov.imagedisplayer.repository.api.data_models.ApiImageDataModel
import me.bohdanov.imagedisplayer.repository.data_base.ImageDbEntity
import me.bohdanov.imagedisplayer.repository.data_base.ImagesDataBase
import me.bohdanov.imagedisplayer.utils.ITEMS_LOADED_PER_PAGE


/**
 * @param handleApiModelsReceived function to convert Api model to DB model.
 *                                placed here because in current realization presumes
 *                                that contains some additional logic
 */
@OptIn(ExperimentalPagingApi::class)
class ImagesRemoteMediator(
    private val database: ImagesDataBase,
    private val apiManager: ApiManager,
    private val handleApiModelsReceived: (ArrayList<ApiImageDataModel>) -> ArrayList<ImageDbEntity>
) : RxRemoteMediator<Int, ImageDbEntity>() {
    private fun getPageToLoadByLoadType(
        loadType: LoadType,
        database: ImagesDataBase
    ): Int {
        return when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> END_REACHED
            LoadType.APPEND -> {
                val numberOfImagesInDb = database.employeeDao().countImagesInDb()
                val numberOfPagesLoadedFromApi = numberOfImagesInDb / ITEMS_LOADED_PER_PAGE
                val nextPageIndex = numberOfPagesLoadedFromApi + 1
                nextPageIndex
            }
        }
    }

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, ImageDbEntity>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                getPageToLoadByLoadType(it, database)
            }
            .flatMap { page ->
                if (page == END_REACHED) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    apiManager.getImagesByPage(pageNumber = page)
                        .map { handleApiModelsReceived(it) }
                        .map { insertToDb(loadType, it) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = (it.size == 0)) }
                        .onErrorReturn { MediatorResult.Error(it) }
                }
            }
    }


    private fun insertToDb(
        loadType: LoadType,
        images: ArrayList<ImageDbEntity>
    ): ArrayList<ImageDbEntity> {
        if (loadType == LoadType.REFRESH) {
            database.employeeDao().clearAll()
        }
        database.employeeDao().insertAll(images)

        return images
    }

    companion object {
        const val END_REACHED = -1
    }
}