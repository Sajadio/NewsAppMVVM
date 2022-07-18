package com.example.newsappmvvm.ui.fragment.article

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.R
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.data.model.LocalArticle
import com.example.newsappmvvm.databinding.FragmentArticleBinding
import com.example.newsappmvvm.ui.base.BaseFragment
import com.example.newsappmvvm.utils.loadImage
import com.example.newsappmvvm.utils.setImage
import com.example.newsappmvvm.utils.setText
import com.example.newsappmvvm.utils.setTexts
import kotlinx.coroutines.flow.collectLatest

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

                binding.saveArticle.setOnClickListener {
                    insert(args.article)
                }

                launchOnLifecycleScope {
                    viewModel.existsItem(args.article.url.toString()).collectLatest { exists ->
                        if (exists) {
                            saveArticle.isClickable = false
                            saveArticle.setImageResource(R.drawable.ic_round_bookmark)
                        }
                    }
                }


//                binding.btnWebView.setOnClickListener {
//                    val build = Bundle()
//                    build.putSerializable("article", args.article)
//                    findNavController().navigate(R.id.action_articleFragment_to_webViewFragment, build)
//                }

            }
        }
    }

}