package com.example.newsappmvvm.ui.fragment.search

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentSearchBinding
import com.example.newsappmvvm.ui.adapter.PagingLoadStateAdapter
import com.example.newsappmvvm.ui.base.BaseFragment
import com.example.newsappmvvm.ui.fragment.explore.ExploreFragmentDirections
import com.example.newsappmvvm.ui.fragment.favorite.FavoriteFragmentDirections
import com.example.newsappmvvm.ui.fragment.search.adapter.SearchPagingAdapter
import com.example.newsappmvvm.utils.observeEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter


@ExperimentalPagingApi
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override val showBottomNav = false
    private lateinit var adapter: SearchPagingAdapter


    override fun launchView() {
        binding.apply {
            with(this@SearchFragment.viewModel) {
                viewModel = this
                clearText.setOnClickListener {
                    searchBox.text.clear()
                }
                searchBox.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {}
                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                        launchOnLifecycleScope {
                            delay(1000L)
                            s.toString().trim().filter { it.toString().isNotEmpty() }.let {
                                getResponseDataByQuery(it)
                                initialAdapter()
                            }
                        }
                    }
                })

                clickBackEvent.observeEvent(viewLifecycleOwner) {
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun initialAdapter() {
        adapter = SearchPagingAdapter(this@SearchFragment.viewModel)
        binding.apply {
            with(adapter) {
                val layoutManager = LinearLayoutManager(context)
                rvSearching.layoutManager = layoutManager
                rvSearching.setHasFixedSize(true)

                swipeRefresh.setOnRefreshListener { refresh() }
                rvSearching.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )

                with(this@SearchFragment.viewModel) {
                    launchOnLifecycleScope {
                        newsQuery.collect { submitData(it) }
                        clickArticleEvent.observeEvent(viewLifecycleOwner) {
                            val action =
                                SearchFragmentDirections.actionSearchFragmentToArticleFragment(it)
                            findNavController().navigate(action)
                        }

                        loadStateFlow.apply {
                            collect {
                                swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                            }
                            distinctUntilChangedBy { it.refresh }
                                .filter { it.refresh is LoadState.NotLoading }
                                .collect { rvSearching.scrollToPosition(0) }
                        }
                    }
                }
            }
        }
    }
}

