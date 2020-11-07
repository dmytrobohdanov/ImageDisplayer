package me.bohdanov.imagedisplayer.repository.data_base

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ImageDbEntity::class], version = 1)
abstract class ImagesDataBase : RoomDatabase() {
    abstract fun imagesDao(): ImagesDao
}