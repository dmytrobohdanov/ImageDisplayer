package me.bohdanov.imagedisplayer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.bohdanov.imagedisplayer.R

class MainActivity : AppCompatActivity() {
//    val repository = Repository(applicationContext = applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}