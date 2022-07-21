package com.example.newsappmvvm.ui.fragment.article

import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentWebViewBinding
import com.example.newsappmvvm.ui.activity.NewsActivity
import com.example.newsappmvvm.ui.base.BaseFragment
import com.example.newsappmvvm.ui.viewmodel.NewsViewModel
import com.example.newsappmvvm.utils.hideSystemUI
import com.example.newsappmvvm.utils.observeEvent

@ExperimentalPagingApi
class WebViewFragment : BaseFragment<FragmentWebViewBinding>(R.layout.fragment_web_view) {

    private val args: WebViewFragmentArgs by navArgs()

    override fun launchView() {
        binding.url = args.article.url
        binding.toolBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

}