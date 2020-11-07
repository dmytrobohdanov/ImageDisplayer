package me.bohdanov.imagedisplayer.repository.data_base

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ImageDbEntity::class], version = 2)
abstract class ImagesDataBase : RoomDatabase() {
    abstract fun employeeDao(): ImagesDao
}