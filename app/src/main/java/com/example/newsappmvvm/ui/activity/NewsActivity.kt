package com.example.newsappmvvm.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.ActivityNewsBinding
import com.example.newsappmvvm.NewsApp
import com.example.newsappmvvm.ui.viewmodel.NewsViewModel
import com.example.newsappmvvm.ui.viewmodel.NewsViewModelFactory
import com.example.newsappmvvm.utils.NetworkHelper
import com.example.newsappmvvm.utils.setVisibility
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

@ExperimentalPagingApi
class NewsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory
    lateinit var viewModel: NewsViewModel

    private lateinit var networkHelper: NetworkHelper
    private lateinit var binding: ActivityNewsBinding
    private var isActive = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as NewsApp).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        binding.lifecycleOwner = this
        networkHelper = NetworkHelper(application)
        connection()
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

    private fun setSnackbar(message: Int, color: Int) {
        Snackbar.make(binding.bottomnavigation, resources.getString(message), Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, color)).show()
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