package ru.app.newsapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.app.newsapp.R
import ru.app.newsapp.adapter.Click
import ru.app.newsapp.adapter.NewsAdapter
import ru.app.newsapp.databinding.FragmentFavoriteNewsBinding
import ru.app.newsapp.fragment.FeedNewsFragment.Companion.stringArgs
import ru.app.newsapp.viewModel.NewsViewModel


class FavoriteNewsFragment : Fragment() {


    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavoriteNewsBinding.inflate(inflater, container, false)

        val adapter = NewsAdapter(
            object : Click {
                override fun toFavorite(articleId: String, isFavorite: Boolean) {
                   viewModel.toFavorite(articleId, isFavorite)
                }

                override fun open(articleUrl: String) {
                    findNavController().navigate(
                        R.id.action_favoriteNewsFragment_to_oneNewsFragment,
                        Bundle().apply {
                            stringArgs = articleUrl
                        }
                    )
                }
            }
        )

        binding.recycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect { uiState ->
                    adapter.submitList(uiState.news.filter { it.isFavorite })
                }
            }
        }



        return binding.root
    }


}