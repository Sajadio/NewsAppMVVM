package com.example.newsappmvvm.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.ActivityNewsBinding
import com.example.newsappmvvm.ui.NewsApp
import com.example.newsappmvvm.ui.viewmodel.NewsViewModel
import com.example.newsappmvvm.ui.viewmodel.NewsViewModelFactory
import com.example.newsappmvvm.utils.NetworkHelper
import com.example.newsappmvvm.utils.setVisibility
import javax.inject.Inject

@ExperimentalPagingApi
class NewsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    private lateinit var networkHelper: NetworkHelper
    private lateinit var binding: ActivityNewsBinding

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as NewsApp).appComponent.inject(this)
        initialViewModel()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        binding.lifecycleOwner = this

        connection()
    }

    private fun initialViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
    }

    private fun connection() {
        networkHelper = NetworkHelper(application, this)
        networkHelper.observe(this) {

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.navHostFragment).navigateUp()
        return true
    }

    override fun onStart() {
        super.onStart()
        binding.bottomnavigation.setupWithNavController(binding.navHostFragment.findNavController())
    }

    fun hidBottomNav(visible: Boolean) {
        setVisibility(binding.bottomnavigation, visible)
    }

    override fun onDestroy() {
        super.onDestroy()
        networkHelper.removeObservers(this)
    }

}