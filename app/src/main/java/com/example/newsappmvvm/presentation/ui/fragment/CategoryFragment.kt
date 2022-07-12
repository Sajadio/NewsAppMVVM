package com.example.newsappmvvm.presentation.ui.fragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentCategoryBinding
import com.example.newsappmvvm.data.model.domen.Article
import com.example.newsappmvvm.presentation.ui.NewsActivity
import com.example.newsappmvvm.presentation.ui.adapter.NewsAdapter
import com.example.newsappmvvm.presentation.ui.adapter.OnClickItemArticle
import com.example.newsappmvvm.presentation.ui.base.BaseFragment

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category),
    OnClickItemArticle {

    private val args: CategoryFragmentArgs by navArgs()

    private lateinit var newsAdapter: NewsAdapter

    override fun initial() {
        (activity as NewsActivity).supportActionBar?.title = args.title?.capitalize().toString()
        binding.viewModel = viewModel
        newsAdapter = NewsAdapter(emptyList(), this)
        binding.recVertical.adapter = newsAdapter
    }

    override fun clickListener(article: Article) {
        val build = Bundle()
        build.putSerializable("article", article)
        findNavController().navigate(R.id.action_categoryFragment_to_articleFragment, build)
    }

}