package com.example.newsappmvvm.ui.fragment.home

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.newsappmvvm.databinding.FragmentHomeBinding
import com.example.newsappmvvm.ui.base.BaseFragment
import com.example.newsappmvvm.R
import com.example.newsappmvvm.ui.fragment.home.adapter.HomeAdapter
import com.example.newsappmvvm.ui.adapter.OnClickItemArticle
import com.example.newsappmvvm.data.model.domen.Article
import com.example.newsappmvvm.ui.adapter.OnClickItemCategory

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), OnClickItemArticle,
    OnClickItemCategory {

    private lateinit var newsAdapter: HomeAdapter

    override fun initial() {
        initialAdapter()
    }

        private fun initialAdapter() {
            newsAdapter = HomeAdapter(emptyList(), this)
            binding.recVertical.adapter = newsAdapter
        }

        override fun clickListener(article: Article) {
            val bundle = Bundle()
            bundle.putSerializable("article", article)
            findNavController().navigate(R.id.action_homeFragment_to_articleFragment, bundle)
        }

    override fun clickListener(category: String) {
        //TODO("Not yet implemented")
    }

}