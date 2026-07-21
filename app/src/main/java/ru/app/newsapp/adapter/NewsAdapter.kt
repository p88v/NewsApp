package ru.app.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import ru.app.newsapp.databinding.OneNewsCardBinding
import ru.app.newsapp.domain.model.NewsArticle


interface Click {

    fun toFavorite(articleId: String, isFavorite: Boolean)
    fun open(articleUrl: String)
}

object NewsDiffUtil : DiffUtil.ItemCallback<NewsArticle>() {
    override fun areItemsTheSame(
        oldItem: NewsArticle,
        newItem: NewsArticle
    ): Boolean {
        return oldItem.articleUrl == newItem.articleUrl
    }

    override fun areContentsTheSame(
        oldItem: NewsArticle,
        newItem: NewsArticle
    ): Boolean {
        return oldItem == newItem
    }
}

class NewsAdapter(private val listner: Click) :
    ListAdapter<NewsArticle, NewsViewHolder>(NewsDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val binding = OneNewsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NewsViewHolder(listner, binding)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        val news = getItem(position)
        holder.bind(news)
    }
}

class NewsViewHolder(private val listner: Click, private val binding: OneNewsCardBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(newsArticle: NewsArticle) {

        binding.dateText.text = newsArticle.publishedAt
        binding.sourceText.text = newsArticle.source
        binding.contentText.text = newsArticle.content
        binding.titleText.text = newsArticle.title
        binding.articleImage.load(newsArticle.imageUrl)

        if(newsArticle.isFavorite){
            binding.favoriteButtonAllReadyAdd.isVisible = true
            binding.favoriteButton.isVisible = false
        } else {
            binding.favoriteButton.isVisible = true
            binding.favoriteButtonAllReadyAdd.isVisible = false
        }

        binding.favoriteButton.setOnClickListener {
                listner.toFavorite(newsArticle.articleUrl, true)
        }

        binding.favoriteButtonAllReadyAdd.setOnClickListener {
                listner.toFavorite(newsArticle.articleUrl, false)
        }

        binding.titleText.setOnClickListener {
            listner.open(newsArticle.articleUrl)
        }
    }
}