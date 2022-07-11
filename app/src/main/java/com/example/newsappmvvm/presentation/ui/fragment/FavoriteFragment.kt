package com.example.newsappmvvm.presentation.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentFavoriteBinding
import com.example.newsappmvvm.data.model.domen.Article
import com.example.newsappmvvm.presentation.ui.adapter.NewsAdapter
import com.example.newsappmvvm.presentation.ui.adapter.OnClickItemArticle
import com.example.newsappmvvm.presentation.ui.base.BaseFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.utils.RecyclerViewSwipe
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite),
    OnClickItemArticle {

    private lateinit var newsAdapter: NewsAdapter

    override val visibilityIconToolbar = listOf(1 )

    override fun initial() {
        binding.viewModel = viewModel
        newsAdapter = NewsAdapter(emptyList(), this)
        binding.favoriteVertical.adapter = newsAdapter
        initialRecyclerSwipe()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
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

    override fun clickListener(article: Article) {
        val bundle = Bundle()
        bundle.putSerializable("article", article)
        findNavController().navigate(R.id.action_favoriteFragment_to_articleFragment, bundle)
    }

    private fun initialRecyclerSwipe() {

        val callback = object : RecyclerViewSwipe(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    viewModel.deleteOneItem(newsAdapter.deleteArticle(viewHolder.adapterPosition))
                    setSnackbar(newsAdapter.deleteArticle(viewHolder.adapterPosition))
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.favoriteVertical)
    }

    private fun setSnackbar(article: Article) {
        Snackbar.make(requireView(), R.string.snackbar_label, Snackbar.LENGTH_LONG)
            .setAction(R.string.action_text) {
                viewModel.insert(article)
            }
            .show()
    }
}