package com.example.newsappmvvm.ui.adapter

import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.data.model.LocalArticle

interface OnItemClickListener {
    fun onClickItemLocalMapArticle(localArticle: LocalArticle)
    fun onClickItemArticle(article: Article)
    fun onClickItemWebView(article: Article)
    fun onClickItemInsert(article: Article)
}