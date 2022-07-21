package com.example.newsappmvvm.ui.base

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.BR
import com.example.newsappmvvm.ui.activity.NewsActivity
import com.example.newsappmvvm.ui.viewmodel.NewsViewModel
import com.example.newsappmvvm.utils.observe

@ExperimentalPagingApi
abstract class BaseFragment<VB : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    Fragment() {

    protected open val showBottomNav: Boolean = true

    abstract fun launchView()

    private lateinit var _binding: VB
    val binding: VB get() = _binding

    // shared viewModel
    lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        (activity as NewsActivity).hidBottomNav(showBottomNav)
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        launchView()
    }

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }
}
