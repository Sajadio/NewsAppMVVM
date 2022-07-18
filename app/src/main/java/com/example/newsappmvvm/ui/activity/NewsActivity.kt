package com.example.newsappmvvm.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.ActivityNewsBinding
import com.example.newsappmvvm.data.local.AppDB
import com.example.newsappmvvm.data.network.ApiProvider
import com.example.newsappmvvm.data.repository.RepositoryImpl
import com.example.newsappmvvm.ui.base.BaseViewModel
import com.example.newsappmvvm.ui.viewmodel.NewsViewModel
import com.example.newsappmvvm.ui.viewmodel.NewsViewModelProvider
import com.example.newsappmvvm.utils.NetworkHelper
import com.example.newsappmvvm.utils.observe
import com.example.newsappmvvm.utils.setVisibility

@ExperimentalPagingApi
class NewsActivity : AppCompatActivity() {

    private lateinit var networkHelper: NetworkHelper
    private lateinit var binding: ActivityNewsBinding

    lateinit var viewModel: NewsViewModel
    private lateinit var viewModelProvider: NewsViewModelProvider
    private lateinit var repository: RepositoryImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialViewModel()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        binding.lifecycleOwner = this

        with(viewModel) {
            observe(errorMessage) { msg ->
                Toast.makeText(this@NewsActivity, msg.getContentIfHandled(), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        connection()
    }

    private fun initialViewModel() {
        repository = RepositoryImpl(ApiProvider.api, AppDB(this))
        viewModelProvider = NewsViewModelProvider(repository)
        viewModel = ViewModelProvider(this, viewModelProvider)[NewsViewModel::class.java]
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