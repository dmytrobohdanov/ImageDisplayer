package me.bohdanov.imagedisplayer.utils

import android.app.Application
import me.bohdanov.imagedisplayer.repository.Repository


class App : Application() {
    private var repository: Repository? = null

    override fun onCreate() {
        super.onCreate()
        repository = Repository(this)
    }

    fun getRepository(): Repository {
        return repository!!
    }
}