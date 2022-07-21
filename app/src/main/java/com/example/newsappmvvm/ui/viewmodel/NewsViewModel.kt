package com.example.newsappmvvm.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.data.model.LocalArticle
import com.example.newsappmvvm.data.repository.RepositoryImpl
import com.example.newsappmvvm.ui.adapter.OnItemClickListener
import com.example.newsappmvvm.utils.event.Event
import com.example.newsappmvvm.utils.listOfCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class NewsViewModel(
    private val repository: RepositoryImpl,
) : ViewModel(), OnItemClickListener {

    private lateinit var _newsBreaking: Flow<PagingData<Article>>
    val newsBreaking: Flow<PagingData<Article>> get() = _newsBreaking

    private lateinit var _newsQuery: Flow<PagingData<Article>>
    val newsQuery: Flow<PagingData<Article>> get() = _newsQuery

    private lateinit var _responseCategories: Flow<PagingData<Article>>
    val responseCategories: Flow<PagingData<Article>> get() = _responseCategories

    val categories = MutableLiveData(listOfCategories)

    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article> get() = _article

    val clickSearchEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val clickBackEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val clickLocalMapperArticleEvent = MutableLiveData<Event<Article>>()
    val clickArticleEvent = MutableLiveData<Event<Article>>()
    val clickWebViewEvent = MutableLiveData<Event<Article>>()
    val toastEvent = MutableLiveData<Event<Boolean>>()
    val existsItemEvent = MutableLiveData<Event<Boolean>>()

    val getSavedArticle: LiveData<List<LocalArticle>> = repository.fetchLocalArticles().asLiveData()

    init {
        viewModelScope.launch {
            _newsBreaking = repository.getBreakingNews().cachedIn(viewModelScope)
        }

    }


    fun getResponseDataByQuery(query: String) {
        viewModelScope.launch {
            _newsQuery = repository.getResponseDataByQuery(query).cachedIn(viewModelScope)
        }
    }

    fun getResponseDataByCategory(category: String) {
        viewModelScope.launch {
            _responseCategories =
                repository.getResponseDataByCategory(category).cachedIn(viewModelScope)
        }
    }

    fun onClickSearch() {
        clickSearchEvent.postValue(Event(true))
    }

    fun onClickBack() {
        clickBackEvent.postValue(Event(true))
    }

    fun deleteOneItem(localArticle: LocalArticle, reInsertItem: Boolean) {
        viewModelScope.launch {
            repository.clearArticle(localArticle, reInsertItem)
        }
    }

    fun deleteAllItem() {
        viewModelScope.launch {
            repository.clearLocalArticles()
        }
    }

    fun existsItem(url: String) = repository.isExistsItem(url)

    override fun onClickItemLocalMapArticle(localArticle: LocalArticle) {
        clickLocalMapperArticleEvent.postValue(
            Event(Article(
                articleId = localArticle.articleId,
                author = localArticle.author,
                content = localArticle.content,
                description = localArticle.description,
                publishedAt = localArticle.publishedAt,
                url = localArticle.url,
                source = localArticle.source,
                urlToImage = localArticle.urlToImage,
            )
            )
        )
    }

    override fun onClickItemArticle(article: Article) {
        clickArticleEvent.postValue(Event(article))
    }

    override fun onClickItemWebView(article: Article) {
        clickWebViewEvent.postValue(Event(article))
    }

    override fun onClickItemInsert(article: Article) {
        viewModelScope.launch {
            toastEvent.postValue(Event((repository.insertLocalArticle(article))))
        }
    }

}

