package com.example.newsappmvvm.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.ActivityNewsBinding
import com.example.newsappmvvm.data.db.local.AppDB
import com.example.newsappmvvm.data.network.ApiProvider
import com.example.newsappmvvm.data.repository.RepositoryImpl
import com.example.newsappmvvm.ui.viewmodel.NewsViewModel
import com.example.newsappmvvm.ui.viewmodel.NewsViewModelProvider
import com.example.newsappmvvm.utils.NetworkHelper
import com.example.newsappmvvm.utils.setVisibility
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalPagingApi
class NewsActivity : AppCompatActivity() {

    private lateinit var networkHelper: NetworkHelper
    private lateinit var binding: ActivityNewsBinding
    private var isActive = false

    lateinit var viewModel: NewsViewModel
    private lateinit var viewModelProvider: NewsViewModelProvider
    private lateinit var repository: RepositoryImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialViewModel()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        binding.lifecycleOwner = this
        networkHelper = NetworkHelper(application)
        connection()
    }

    private fun initialViewModel() {
        repository = RepositoryImpl(ApiProvider.api, AppDB(this))
        viewModelProvider = NewsViewModelProvider(repository)
        viewModel = ViewModelProvider(this, viewModelProvider)[NewsViewModel::class.java]
    }

    private fun connection() {
        networkHelper.observe(this) { message ->
            checkStateNetwork(message)
        }
    }

    private fun checkIsNetworkActive(message: Int, color: Int) {
        if (isActive) {
            setSnackbar(message, color)
            isActive = false
        }
    }

    private fun checkStateNetwork(message: Int) {
        when (message) {
            R.string.connected -> {
                checkIsNetworkActive(message, R.color.green)
                isActive = true
            }
            R.string.noConnection -> {
                setSnackbar(message, R.color.red)
                isActive = true
            }
            R.string.notActive -> {
                setSnackbar(message, R.color.black)
                isActive = true
            }
        }
    }

    private fun setSnackbar(message: Int, color: Int)  {
        Snackbar.make(binding.bottomnavigation,resources.getString(message),Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this,color)).show()
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