package com.example.newsappmvvm.ui.fragment.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.databinding.ItemArticleBinding
import com.example.newsappmvvm.ui.adapter.OnItemClickListener

class BreakingNewsPagingAdapter
    (
    private val clickListener: OnItemClickListener
    ) : PagingDataAdapter<Article, BreakingNewsPagingAdapter.ArticleHolder>(CharacterComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArticleHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class ArticleHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Article) = apply {
            binding.apply {
                items = item
                listener = clickListener
                executePendingBindings()
            }
        }
    }

    object CharacterComparator : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.articleId == newItem.articleId

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }
}