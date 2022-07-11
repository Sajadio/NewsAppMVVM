package com.example.newsappmvvm.utils

import android.annotation.SuppressLint
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.R
import com.example.newsappmvvm.data.model.domen.Article
import com.example.newsappmvvm.ui.fragment.home.adapter.HomeAdapter


@SuppressLint("NotifyDataSetChanged")
@BindingAdapter(value = ["app:adapter"])
fun RecyclerView.setAdapter(items: List<Article>?) {
    this.apply {
        setHasFixedSize(true)
        val adapter = adapter as HomeAdapter
        items?.let { adapter.updateData(it) }
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter(value = ["app:url"])
fun ImageView.setImage(url: String?) {
    url?.let { this.loadImage(it) }
}

@BindingAdapter(value = ["app:visibleView"])
fun View.visibleView(value: Int?) {
    if (value == 0)
        this.visibility = VISIBLE
    else
        this.visibility = INVISIBLE
}

@BindingAdapter(value = ["app:loading"])
fun <T> View.loading(state: NetworkStatus<T>?) {
    when (state) {
        is NetworkStatus.Loading -> this.visibility = VISIBLE
        is NetworkStatus.Success -> this.visibility = INVISIBLE
        is NetworkStatus.Failure -> this.visibility = INVISIBLE
    }
}

@BindingAdapter(value = ["app:connection"])
fun View.connection(connection: Int?) {
    when (connection) {
        R.string.connection -> this.visibility = INVISIBLE
        R.string.noConnection -> this.visibility = VISIBLE
        R.string.noInternet -> this.visibility = VISIBLE
    }
}

@BindingAdapter(value = ["app:textStateConnection"])
fun TextView.setText(text: Int?) {
    this.text = text?.let { resources.getString(it) }
}


@BindingAdapter(value = ["app:src"])
fun ImageView.setImage(img: Int?) {
    img?.let { this.setImageResource(it) }
}



