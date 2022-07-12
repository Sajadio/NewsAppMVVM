package com.example.newsappmvvm.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.ItemArticleBinding
import com.example.newsappmvvm.data.model.domen.Article
import com.example.newsappmvvm.ui.adapter.OnClickItemArticle
import com.example.newsappmvvm.utils.DifferCallbacks


class HomeAdapter(
    private var items: List<Article>,
    private val listener: OnClickItemArticle
) : RecyclerView.Adapter<HomeAdapter.NewsHolder>() {

    fun updateData(item: List<Article>) {
        items = item
        differ.submitList(items)
    }

    fun deleteArticle(position: Int): Article {
        return differ.currentList[position]
    }

    private val differ = AsyncListDiffer(this, DifferCallbacks)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_article, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            items = article
            cardArticle.setOnClickListener {
                listener.clickListener(article)
            }
            executePendingBindings()
        }
    }

    override fun getItemCount() = differ.currentList.size

    class NewsHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

}