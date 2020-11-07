package me.bohdanov.imagedisplayer.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
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
import java.io.FileInputStream
import java.io.InputStream
import java.net.URL
import java.util.*


class Repository(private val applicationContext: Context) {
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
//        return ArrayList(apiImageModelList.map { ImageDbEntity.createFrom(it, "") })

        return ArrayList(apiImageModelList.map { apiModel ->
            val cashedFileName = loadImageToLocalStorage(apiModel)
            ImageDbEntity.createFrom(apiModel, cashedFileName)
        })
    }

    private fun loadImageToLocalStorage(apiModel: ApiImageDataModel): String {
        Log.d("piing", "loading: ${apiModel.id}")
        //getting new link with smaller image to create cashed local image
        val fileUrl = getSmallerImageFileUrl(apiModel)

        val inputStream: InputStream = URL(fileUrl).openStream()
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        val cashedFileName = apiModel.id
        saveImage(applicationContext, bitmap, cashedFileName)

        return cashedFileName
    }

    /**
     * all the images can be loaded in any size by link:
     * https://picsum.photos/id/{imageId}/{newWidth}/{newHeight}
     *
     * @return new link with small image
     */
    private fun getSmallerImageFileUrl(apiModel: ApiImageDataModel): String {
        // requesting image with 600px height and width is calculated by ratio of image
        val height = 600
        val width = height * (apiModel.height / apiModel.width)
        return "https://picsum.photos/id/${apiModel.id}/${width}/${height}"
    }

    ///---------------------
    fun saveImage(context: Context, b: Bitmap, imageName: String?) {
        try {
            val foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE)
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream)
            foStream.close()
        } catch (e: Exception) {
            Log.d("image error:", "Something went wrong! >> 1")
            e.printStackTrace()
        }
    }

    companion object {
        fun loadImageBitmap(context: Context, imageName: String?): Bitmap? {
            var bitmap: Bitmap? = null
            val fiStream: FileInputStream
            try {
                fiStream = context.openFileInput(imageName)
                bitmap = BitmapFactory.decodeStream(fiStream)
                fiStream.close()
            } catch (e: java.lang.Exception) {
                Log.d("image error:", "Something went wrong! >> 2")
                e.printStackTrace()
            }
            return bitmap
        }
    }
}