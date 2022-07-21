package com.example.newsappmvvm.ui.fragment.home

import android.util.Log
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappmvvm.databinding.FragmentHomeBinding
import com.example.newsappmvvm.ui.base.BaseFragment
import com.example.newsappmvvm.R
import com.example.newsappmvvm.ui.activity.NewsActivity
import com.example.newsappmvvm.ui.adapter.PagingLoadStateAdapter
import com.example.newsappmvvm.ui.fragment.home.adapter.BreakingNewsPagingAdapter
import com.example.newsappmvvm.utils.UiMode
import com.example.newsappmvvm.utils.observeEvent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@ExperimentalPagingApi
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var adapter: BreakingNewsPagingAdapter

    override fun launchView() {
        binding.apply {
            vm = viewModel
            adapter = BreakingNewsPagingAdapter(viewModel)

            changeTheme.setOnClickListener {
                setupDialog()
            }

            with(adapter) {
                swipeRefresh.setOnRefreshListener { refresh() }

                val layoutManager = LinearLayoutManager(context)
                rvBreakingNews.layoutManager = layoutManager
                rvBreakingNews.setHasFixedSize(true)

                rvBreakingNews.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )

                with(viewModel) {

                    clickArticleEvent.observeEvent(viewLifecycleOwner){
                        val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(it)
                        findNavController().navigate(action)
                    }

                    clickSearchEvent.observeEvent(viewLifecycleOwner) {
                        findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                    }

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

            }
        }
    }

    private fun setupDialog() {
        val arrayAdapter =
            ArrayAdapter<String>((activity as NewsActivity),
                android.R.layout.select_dialog_singlechoice)
        arrayAdapter.add(resources.getString(R.string.light))
        arrayAdapter.add(resources.getString(R.string.dark))

        MaterialAlertDialogBuilder(requireContext())
            .setSingleChoiceItems(arrayAdapter, (activity as NewsActivity).checkCurrentMode()) { dialog, which ->
                when (arrayAdapter.getItem(which)) {
                    resources.getString(R.string.light) -> setupTheme(UiMode.LIGHT)
                    resources.getString(R.string.dark) -> setupTheme(UiMode.DARK)
                }
                dialog.cancel()
            }.create()
            .show()

    }


    private fun setupTheme(uiMode: UiMode) {
        (activity as NewsActivity).viewModel.changeSelectedTheme(uiMode)
    }
}