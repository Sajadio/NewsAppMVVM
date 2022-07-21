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
import com.example.newsappmvvm.data.db.ArticleDatabase
import com.example.newsappmvvm.data.repository.Repository
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
        binding.btnRetry.setOnClickListener {
            connection()
        }


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        setupActionBarWithNavController(navHostFragment.navController)
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_home, menu)

        val searchItem = menu?.findItem(R.id.searching)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = false

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty())
                    viewModel.getSearchingQuery(newText)
                else
                    viewModel.getSearchingQuery("sport")
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(
            findNavController(R.id.navHostFragment)
        ) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        networkHelper.removeObservers(this)
    }

}