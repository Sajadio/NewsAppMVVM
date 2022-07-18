package com.example.newsappmvvm.ui.fragment.home

import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappmvvm.databinding.FragmentHomeBinding
import com.example.newsappmvvm.ui.base.BaseFragment
import com.example.newsappmvvm.R
import com.example.newsappmvvm.ui.adapter.PagingLoadStateAdapter
import com.example.newsappmvvm.ui.fragment.favorite.FavoriteFragmentDirections
import com.example.newsappmvvm.ui.fragment.home.adapter.BreakingNewsPagingAdapter
import com.example.newsappmvvm.utils.observeEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@ExperimentalPagingApi
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var adapter: BreakingNewsPagingAdapter

    override fun launchView() {
        binding.apply {
            vm = viewModel
            adapter = BreakingNewsPagingAdapter()

            with(adapter) {
                swipeRefresh.setOnRefreshListener { refresh() }

                val layoutManager = LinearLayoutManager(context)
                rvBreakingNews.layoutManager = layoutManager
                rvBreakingNews.setHasFixedSize(true)

                rvBreakingNews.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )

                onItemClickListener { article ->
                    article?.let {
                        val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(article)
                        findNavController().navigate(action)
                    }
                }

                with(viewModel) {
                    launchOnLifecycleScope {
                        newsBreaking.collectLatest {
                            submitData(it)
                        }

                        loadStateFlow.apply {

                            distinctUntilChangedBy { it.refresh }
                                .filter { it.refresh is LoadState.NotLoading }
                                .collect { rvBreakingNews.scrollToPosition(0) }

                            collectLatest {
                                swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                            }

                        }

                    }
                }

                viewModel.clickSearchEvent.observeEvent(viewLifecycleOwner) {
                    findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                }
            }
        }
    }
}