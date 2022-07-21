package com.example.newsappmvvm.ui.fragment.favorite

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentFavoriteBinding
import com.example.newsappmvvm.ui.base.BaseFragment
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.data.model.LocalArticle
import com.example.newsappmvvm.ui.activity.NewsActivity
import com.example.newsappmvvm.ui.fragment.favorite.adapter.FavoriteAdapter
import com.example.newsappmvvm.utils.RecyclerViewSwipe
import com.example.newsappmvvm.utils.observeEvent
import com.example.newsappmvvm.utils.setToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

@ExperimentalPagingApi
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    override fun launchView() {
        binding.vm = viewModel
        initialRecyclerSwipe()
        binding.deleteAllItems.setOnClickListener {
            deleteAllArticles()
        }
    }

    private fun initialRecyclerSwipe() = with(binding) {
        with(viewModel) {
            val adapter = FavoriteAdapter(viewModel)

            val layoutManager = LinearLayoutManager(context)
            rvFavorite.layoutManager = layoutManager
            rvFavorite.setHasFixedSize(true)
            binding.rvFavorite.adapter = adapter

            getSavedArticle.observe(viewLifecycleOwner) {
                deleteAllItems.isVisible = it.isNotEmpty()
                stateFavorite.isVisible = it.isEmpty()
                adapter.updateData(it)
            }

            clickLocalMapperArticleEvent.observeEvent(viewLifecycleOwner) {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToArticleFragment(it)
                findNavController().navigate(action)
            }

            val callback = object : RecyclerViewSwipe(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (direction == ItemTouchHelper.LEFT) {
                        viewModel.deleteOneItem(adapter.deleteArticle(viewHolder.layoutPosition),
                            false)
                        setSnackbar(adapter.deleteArticle(viewHolder.layoutPosition))
                    }
                }
            }
            val itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper.attachToRecyclerView(rvFavorite)
        }

    }


    private fun deleteAllArticles() {
        MaterialAlertDialogBuilder(
            requireContext(),
            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog
        )
            .setMessage(resources.getString(R.string.message_snack_bar))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                viewModel.deleteAllItem()
                requireContext().setToast(R.string.un_favorite_content)
            }
            .show()
    }

    private fun setSnackbar(deleteArticle: LocalArticle) {
        Snackbar.make(requireView(), R.string.message_snack_bar, Snackbar.LENGTH_LONG)
            .setAction(R.string.action_text) {
                viewModel.deleteOneItem(deleteArticle, true)
            }
            .show()
    }
}