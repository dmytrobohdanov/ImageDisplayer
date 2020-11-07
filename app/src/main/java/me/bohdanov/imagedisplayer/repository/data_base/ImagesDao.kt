package me.bohdanov.imagedisplayer.repository.data_base

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ImagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<ImageDbEntity>)


    @Query("SELECT COUNT(*) FROM images")
    fun countImagesInDb(): Int

    @Query("SELECT * FROM images")
    fun pagingSource(): PagingSource<Int, ImageDbEntity>

    @Query("DELETE FROM images")
    fun clearAll()
}