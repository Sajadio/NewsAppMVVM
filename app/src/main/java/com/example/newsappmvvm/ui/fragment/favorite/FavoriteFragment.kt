package com.example.newsappmvvm.ui.fragment.favorite

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentFavoriteBinding
import com.example.newsappmvvm.ui.base.BaseFragment
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.data.model.LocalArticle
import com.example.newsappmvvm.ui.adapter.CommonAdapter
import com.example.newsappmvvm.ui.fragment.search.SearchFragmentDirections
import com.example.newsappmvvm.utils.RecyclerViewSwipe
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

    private fun initialRecyclerSwipe() {
        val adapter = CommonAdapter()

        val layoutManager = LinearLayoutManager(context)
        binding.rvFavorite.layoutManager = layoutManager
        binding.rvFavorite.setHasFixedSize(true)
        viewModel.getSavedArticle.observe(this){
        adapter.updateData(it) }
        binding.rvFavorite.adapter = adapter

        adapter.onItemClickListener { localArticle ->
            localArticle?.let {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToArticleFragment(viewModel.mapArticle(localArticle))
                findNavController().navigate(action)
            }
        }


        val callback = object : RecyclerViewSwipe(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    viewModel.deleteOneItem(adapter.deleteArticle(viewHolder.layoutPosition),false)
                    setSnackbar(adapter.deleteArticle(viewHolder.layoutPosition))
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvFavorite)
    }


    private fun deleteAllArticles() {
        MaterialAlertDialogBuilder(
            requireContext(),
            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog
        )
            .setMessage(resources.getString(R.string.long_message))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                viewModel.deleteAllItem()
            }
            .show()
    }

    private fun setSnackbar(deleteArticle: LocalArticle) {
        Snackbar.make(requireView(), R.string.snackbar_label, Snackbar.LENGTH_LONG)
            .setAction(R.string.action_text) {
                viewModel.deleteOneItem(deleteArticle,true)
            }
            .show()
    }
}