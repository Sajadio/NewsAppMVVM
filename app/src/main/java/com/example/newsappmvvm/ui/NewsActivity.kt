package com.example.newsappmvvm.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.ActivityNewsBinding
import com.example.newsappmvvm.data.model.db.ArticleDatabase
import com.example.newsappmvvm.data.model.repository.Repository
import com.example.newsappmvvm.ui.viewmodel.NewsViewModel
import com.example.newsappmvvm.ui.viewmodel.NewsViewModelProvider
import com.example.newsappmvvm.utils.NetworkHelper

class NewsActivity : AppCompatActivity() {

    private lateinit var networkHelper: NetworkHelper
    private lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel
    private lateinit var viewModelProvider: NewsViewModelProvider
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialViewModel()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        connection()

    }


    private fun initialViewModel() {
        repository = Repository(ArticleDatabase(this))
        viewModelProvider = NewsViewModelProvider(repository)
        viewModel = ViewModelProvider(this, viewModelProvider)[NewsViewModel::class.java]
    }

    private fun connection() {
        networkHelper = NetworkHelper(application,this)
        networkHelper.observe(this) {
            viewModel.connection.postValue(it)
            if (it == R.string.connection)
                viewModel.getSearchingQuery("sport")
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(
            findNavController(R.id.fragment_host)
        ) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.fragment_host).navigateUp()
        return true
    }

    override fun onStart() {
        super.onStart()
        binding.bottomnavigation.setupWithNavController(findNavController(R.id.fragment_host))
    }

    override fun onDestroy() {
        super.onDestroy()
        networkHelper.removeObservers(this)
    }

}