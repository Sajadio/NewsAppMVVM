package com.example.newsappmvvm.ui.fragment.article

import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentWebViewBinding
import com.example.newsappmvvm.ui.base.BaseFragment
import com.example.newsappmvvm.ui.viewmodel.NewsViewModel

@ExperimentalPagingApi
class WebViewFragment : BaseFragment<FragmentWebViewBinding>(R.layout.fragment_web_view) {


    //    override fun initial() {
////        (activity as NewsActivity).supportActionBar?.title = args.article.source?.name
////        binding.lifecycleOwner = viewLifecycleOwner
////        binding.webView.apply {
////            loadUrl(args.article.url.toString())
////        }
//    }

    override fun launchView() {
//        TODO("Not yet implemented")
    }

}