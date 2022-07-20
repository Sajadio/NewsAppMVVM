package com.example.newsappmvvm.ui.fragment.explore

import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentExploreBinding
import com.example.newsappmvvm.ui.adapter.PagingLoadStateAdapter
import com.example.newsappmvvm.ui.base.BaseFragment
import com.example.newsappmvvm.ui.fragment.explore.adapter.ExplorePagingAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@ExperimentalPagingApi
class ExploreFragment :
    BaseFragment<FragmentExploreBinding>(R.layout.fragment_explore) {
    private lateinit var adapter: ExplorePagingAdapter

    override fun launchView() {
        setUpTabLayout()
    }

    private fun initialAdapter() {
        binding.apply {
            adapter = ExplorePagingAdapter()
            with(adapter) {

                val layoutManager = LinearLayoutManager(context)
                rvExplore.layoutManager = layoutManager
                rvExplore.setHasFixedSize(true)

                swipeRefresh.setOnRefreshListener { refresh() }
                rvExplore.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )

                with(viewModel) {
                    launchOnLifecycleScope { responseCategories.collect { submitData(it) } }

                    onItemClickListener { article ->
                        article?.let {
                            val action = ExploreFragmentDirections.actionExploreFragmentToArticleFragment(article)
                            findNavController().navigate(action)
                        }
                    }

                    launchOnLifecycleScope {

                        loadStateFlow.apply {
                            collect {
                                swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                            }

                            distinctUntilChangedBy { it.refresh }
                                .filter { it.refresh is LoadState.NotLoading }
                                .collect { rvExplore.scrollToPosition(0) }
                        }
                    }

                }
            }

        }

    }

    private fun setUpTabLayout() = with(binding) {
        with(viewModel) {
            categories.observe(viewLifecycleOwner) { data ->
                data.forEach {
                    tabLayout.addTab(tabLayout.newTab().setText(resources.getString(it)))
                }
            }

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    getResponseDataByCategory(tab.text.toString())
                    initialAdapter()
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            }
            )
        }
    }
}
