package com.example.newsappmvvm.ui.fragment.article

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentArticleBinding
import com.example.newsappmvvm.ui.base.BaseFragment
import com.example.newsappmvvm.utils.observeEvent
import com.example.newsappmvvm.utils.setToast
import kotlinx.coroutines.flow.collectLatest


@ExperimentalPagingApi
class ArticleFragment : BaseFragment<FragmentArticleBinding>(R.layout.fragment_article) {

    private val args: ArticleFragmentArgs by navArgs()

    override val showBottomNav = false
    override fun launchView() {
        binding.apply {
            with(this@ArticleFragment.viewModel) {
                viewModel = this
                items = args.article

                args.article.url?.let {
                    launchOnLifecycleScope {
                        checkExistsItem(it).collectLatest {
                            if (it) {
                                saveArticle.setImageResource(R.drawable.ic_round_bookmark)
                                saveArticle.isClickable = false
                            }
                        }
                    }
                }

                clickBackEvent.observeEvent(viewLifecycleOwner) {
                    findNavController().navigateUp()
                }
                clickWebViewEvent.observeEvent(viewLifecycleOwner) {
                    val action =
                        ArticleFragmentDirections.actionArticleFragmentToWebViewFragment(it)
                    findNavController().navigate(action)
                }
                toastEvent.observeEvent(viewLifecycleOwner) {
                    if (it)
                        requireContext().setToast(R.string.favorite_content)
                }
            }
        }
    }

}