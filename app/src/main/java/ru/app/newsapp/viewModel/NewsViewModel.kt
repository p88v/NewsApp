package ru.app.newsapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.IOException
import ru.app.newsapp.data.local.appDb.AppDb
import ru.app.newsapp.data.repositoryImpl.NewsArticleRepositoryImpl
import ru.app.newsapp.domain.model.NewsArticle
import ru.app.newsapp.ui.RequestState
import ru.app.newsapp.ui.UiState

class NewsViewModel(application: Application) : AndroidViewModel(application) {


    val dao = AppDb.getInstance(application).getDao()
    val repository: NewsArticleRepositoryImpl = NewsArticleRepositoryImpl(dao)

    private val requestState = MutableStateFlow(RequestState())

    val uiState: StateFlow<UiState> = combine(
        repository.getAll(),
        requestState
    ) { news, request ->

        UiState(
            news = news,
            loading = requestState.value.loading,
            error = requestState.value.error,
            empty = news.isEmpty()
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState(loading = true)
    )


    fun toFavorite(articleId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toFavorite(articleId, isFavorite)
        }
    }


    fun getById(stringUrl: String): NewsArticle? {
        return uiState.value.news.find { it.articleUrl == stringUrl }
    }

    fun refresh() {
        viewModelScope.launch {
            requestState.value = RequestState(loading = true)
            try {
                requestState.value = RequestState(loading = false)
                repository.refresh()
            } catch (e: IOException) {
                requestState.value =
                    RequestState(loading = false, error = "Проверьте подключение к интернету")
            } catch (e: Exception) {
                requestState.value = RequestState(loading = false, error = "Неизвестная ошибка")
            }

        }
    }

}