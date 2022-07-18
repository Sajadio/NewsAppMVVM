package com.example.newsappmvvm.ui.fragment.explore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.databinding.ItemCommonBinding

class ExplorePagingAdapter :
    PagingDataAdapter<Article, ExplorePagingAdapter.SearchViewHolder>(CharacterComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchViewHolder(
            ItemCommonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    private var onItemClickListener: ((Article?) -> Unit)? = null
    fun onItemClickListener(listener: (Article?) -> Unit) {
        onItemClickListener = listener
    }

    inner class SearchViewHolder(private val binding: ItemCommonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Article) = apply {
            binding.apply {
                items = item
                root.setOnClickListener {
                    onItemClickListener?.let { (it(item)) }
                }
                executePendingBindings()
            }

        }
    }

    object CharacterComparator : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }

}