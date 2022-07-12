package com.example.newsappmvvm.presentation.ui.fragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.newsappmvvm.databinding.FragmentHomeBinding
import com.example.newsappmvvm.presentation.ui.base.BaseFragment
import com.example.newsappmvvm.R
import com.example.newsappmvvm.data.model.domen.Category
import com.example.newsappmvvm.presentation.ui.adapter.CategoryAdapter
import com.example.newsappmvvm.presentation.ui.adapter.NewsAdapter
import com.example.newsappmvvm.presentation.ui.adapter.OnClickItemArticle
import com.example.newsappmvvm.utils.categories
import com.example.newsappmvvm.data.model.domen.Article
import com.example.newsappmvvm.presentation.ui.adapter.OnClickItemCategory
import com.example.newsappmvvm.utils.categoryImg

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), OnClickItemArticle,
    OnClickItemCategory {

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun initial() {
        initialAdapter()
    }

        private fun initialAdapter() {
            newsAdapter = NewsAdapter(emptyList(), this)
            binding.recVertical.adapter = newsAdapter

            val category = mutableListOf<Category>()
            for (i in 0..6) {
                category.add(Category(categories[i], categoryImg[i]))
            }

            categoryAdapter = CategoryAdapter(category, this)
            binding.recHorizontal.adapter = categoryAdapter
            binding.viewModel = viewModel
        }

        override fun clickListener(article: Article) {
            val bundle = Bundle()
            bundle.putSerializable("article", article)
            findNavController().navigate(R.id.action_homeFragment_to_articleFragment, bundle)
        }

        override fun clickListener(category: String) {
            viewModel.getBreakingNews(category)
            val bundle = Bundle()
            bundle.putString("title", category)
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
        }

    }