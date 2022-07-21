package com.example.newsappmvvm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.ItemCategoryBinding
import com.example.newsappmvvm.data.model.Category

class CategoryAdapter(
    private val items:List<Category>,
    private val listener: OnClickItemCategory
    ):  RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_category, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val category = items[position]
        holder.binding.apply {
            items = category
            cardCategory.setOnClickListener {
                listener.clickListener(tvCategory.text.toString())
            }
            executePendingBindings()
        }

    }

    override fun getItemCount() = items.size

    class CategoryHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

}