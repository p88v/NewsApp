package ru.app.newsapp.fragment

import android.os.Bundle
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
import ru.app.newsapp.viewModel.NewsViewModel
import ru.app.newsapp.R
import ru.app.newsapp.adapter.Click
import ru.app.newsapp.adapter.NewsAdapter
import ru.app.newsapp.databinding.FragmentFeedNewsBinding
import ru.app.newsapp.utils.StringArg
import kotlin.getValue

class FeedNewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedNewsBinding.inflate(inflater, container, false)

        viewModel.refresh()
        val adapter = NewsAdapter(
            object : Click {
                override fun toFavorite(articleId: String, isFavorite: Boolean) {
                    viewModel.toFavorite(articleId, isFavorite)
                }

                override fun open(articleUrl: String) {
                    findNavController().navigate(
                        R.id.action_feedNewsFragment_to_oneNewsFragment,
                        Bundle().apply {
                            stringArgs = articleUrl
                        }
                    )
                }
            }
        )

        binding.recycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    adapter.submitList(it.news)
                }
            }
        }

        binding.favoriteText.setOnClickListener {
            findNavController().navigate(
                R.id.action_feedNewsFragment_to_favoriteNewsFragment
            )
        }




        return binding.root
    }

    companion object {
        var Bundle.stringArgs by StringArg
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.refresh()
    }
}