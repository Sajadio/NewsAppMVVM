package com.example.newsappmvvm.ui.fragment.article

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentArticleBinding
import com.example.newsappmvvm.ui.base.BaseFragment
import com.example.newsappmvvm.utils.observeEvent


@ExperimentalPagingApi
class ArticleFragment : BaseFragment<FragmentArticleBinding>(R.layout.fragment_article) {

    private val args: ArticleFragmentArgs by navArgs()

    override val showBottomNav = false
    override fun launchView() {
        binding.apply {
            with(viewModel) {
                vm = viewModel
                items = args.article
                binding.toolBar.setNavigationOnClickListener {
                    findNavController().navigateUp()
                }
                clickWebViewEvent.observeEvent(viewLifecycleOwner) {
                    val action =
                        ArticleFragmentDirections.actionArticleFragmentToWebViewFragment(it)
                    findNavController().navigate(action)
                }
            }
        }
    }

}