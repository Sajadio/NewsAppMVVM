package com.example.newsappmvvm.utils

import android.annotation.SuppressLint
import android.view.View
import android.view.View.*
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.model.domen.Article
import com.example.newsappmvvm.ui.adapter.NewsAdapter

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter(value = ["app:adapter"])
fun RecyclerView.setAdapter(items: List<Article>?) {
    this.apply {
        setHasFixedSize(true)
        val adapter = adapter as NewsAdapter
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
fun View.connection(connection: Boolean?) {
    if (connection == true)
        this.visibility = INVISIBLE
    else
        this.visibility = VISIBLE

}

@BindingAdapter(value = ["app:src"])
fun ImageView.setImage(img: Int?) {
    img?.let { this.setImageResource(it) }
}



