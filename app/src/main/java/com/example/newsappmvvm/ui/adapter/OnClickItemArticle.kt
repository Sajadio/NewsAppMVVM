package com.example.newsappmvvm.ui.adapter

import com.example.newsappmvvm.data.model.Article

interface OnClickItemArticle {
    fun clickListener(article: Article)
}