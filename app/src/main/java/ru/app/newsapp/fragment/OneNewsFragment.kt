package ru.app.newsapp.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import kotlinx.coroutines.launch
import ru.app.newsapp.viewModel.NewsViewModel
import ru.app.newsapp.adapter.Click
import ru.app.newsapp.adapter.NewsAdapter
import ru.app.newsapp.databinding.FragmentOneNewsBinding
import ru.app.newsapp.domain.model.NewsArticle
import ru.app.newsapp.fragment.FeedNewsFragment.Companion.stringArgs

class OneNewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()
    private var article: NewsArticle? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOneNewsBinding.inflate(inflater, container, false)

        val arg = arguments?.stringArgs

        val adapter = NewsAdapter(
            object : Click {
                override fun toFavorite(articleId: String, isFavorite: Boolean) {
                    viewModel.toFavorite(articleId, isFavorite)
                }

                override fun open(articleUrl: String) {
                    Toast.makeText(
                        parentFragment?.context,
                        "Вы уже открыли статью",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    article = viewModel.getById(
                        arg ?: throw NullPointerException("Пришел пустой идентификатор")
                    )

                    article?.let { art->
                        binding.titleText.text = art.title
                        binding.contentText.text = art.content
                        binding.sourceText.text = art.source
                        binding.dateText.text = art.publishedAt
                        binding.articleImage.load(art.imageUrl)


                        if (art.isFavorite) {
                            binding.favoriteButtonAdd.isVisible = true
                            binding.favoriteButton.isVisible = false
                        } else {
                            binding.favoriteButton.isVisible = true
                            binding.favoriteButtonAdd.isVisible = false
                        }

                        binding.favoriteButton.setOnClickListener {
                            viewModel.toFavorite(art.articleUrl, true)
                        }

                        binding.favoriteButtonAdd.setOnClickListener {
                            viewModel.toFavorite(art.articleUrl, false)
                        }


                    }
                }
            }
        }

        binding.openOriginalButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(article?.articleUrl)
            )

            startActivity(intent)
        }

        return binding.root
    }


}