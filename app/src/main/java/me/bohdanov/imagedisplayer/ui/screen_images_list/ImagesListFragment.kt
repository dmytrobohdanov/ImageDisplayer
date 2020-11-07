package me.bohdanov.imagedisplayer.ui.screen_images_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import kotlinx.android.synthetic.main.fragment_images_list.*
import me.bohdanov.imagedisplayer.R


class ImagesListFragment : Fragment() {
    val viewModel: ImagesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_images_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imagesAdapter = ImagesRvAdapter()
        fragment_images_list_rv.adapter = imagesAdapter

        viewModel.imagesPagedList.observe(viewLifecycleOwner) { pagingData ->
            imagesAdapter.submitData(lifecycle, pagingData)
        }

        imagesAdapter.addLoadStateListener { loadStates ->
            fragment_images_list_progress.isVisible = loadStates.refresh is LoadState.Loading
        }
    }
}