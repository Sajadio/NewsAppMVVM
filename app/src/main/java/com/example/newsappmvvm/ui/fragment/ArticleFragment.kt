package com.example.newsappmvvm.ui.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentArticleBinding
import com.example.newsappmvvm.ui.base.BaseFragment
import com.example.newsappmvvm.ui.NewsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : BaseFragment<FragmentArticleBinding>(R.layout.fragment_article) {

    private val args: ArticleFragmentArgs by navArgs()
    override val visibilityIconToolbar = listOf(
        R.id.searching,
        R.id.deleteAllItem
    )

    override fun initial() {
        (activity as NewsActivity).supportActionBar?.title = args.article.source?.name
        binding.viewModel = viewModel
        binding.items = args.article


        viewModel.existsItem(args.article.url.toString()).observe(this) { exists ->
            if (exists) {
                binding.btnSave.isClickable = false
                binding.btnSave.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
            }

        }

        binding.btnWebView.setOnClickListener {
            val build = Bundle()
            build.putSerializable("article", args.article)
            findNavController().navigate(R.id.action_articleFragment_to_webViewFragment, build)
        }

    }
}