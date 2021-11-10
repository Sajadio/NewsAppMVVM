package com.example.newsappmvvm.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.newsappmvvm.model.domen.Article

object DifferCallbacks : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}