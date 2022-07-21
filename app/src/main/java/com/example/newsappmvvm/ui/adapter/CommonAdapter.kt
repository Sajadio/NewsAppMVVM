package com.example.newsappmvvm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.R
import com.example.newsappmvvm.data.model.LocalArticle
import com.example.newsappmvvm.databinding.ItemFavoriteBinding

class CommonAdapter(
    private val clickListener: OnItemClickListener,
) : RecyclerView.Adapter<CommonAdapter.CommonHolder>() {

    private var onItemClickListener: ((LocalArticle?) -> Unit)? = null
    fun onItemClickListener(listener: (LocalArticle?) -> Unit) {
        onItemClickListener = listener
    }

    fun updateData(item: List<LocalArticle>) {
        differ.submitList(item)
    }

    fun deleteArticle(position: Int): LocalArticle {
        return differ.currentList[position]
    }

    private val differ = AsyncListDiffer(this, DifferCallbacks)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonHolder {
        return CommonHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_favorite, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CommonHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            items = article
            listener = clickListener
            executePendingBindings()
        }
    }

    override fun getItemCount() = differ.currentList.size

    inner class CommonHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    object DifferCallbacks : DiffUtil.ItemCallback<LocalArticle>() {
        override fun areItemsTheSame(oldItem: LocalArticle, newItem: LocalArticle) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: LocalArticle, newItem: LocalArticle): Boolean {
            return oldItem == newItem
        }
    }
}