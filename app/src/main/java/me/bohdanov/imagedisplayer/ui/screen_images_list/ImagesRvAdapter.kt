package me.bohdanov.imagedisplayer.ui.screen_images_list

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*
import me.bohdanov.imagedisplayer.R
import me.bohdanov.imagedisplayer.ui.ui_data_models.UiImageDataModel

class ImagesRvAdapter :
    PagingDataAdapter<UiImageDataModel, ImagesRvAdapter.ImageViewHolder>(UiImageDataModel.getDiffCallback()) {
    class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Picasso.get()
            .load(Uri.parse(getItem(position)?.webUrl))
            .into(holder.view.item_image_pic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

}