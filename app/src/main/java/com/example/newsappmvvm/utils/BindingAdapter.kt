@file:Suppress("ControlFlowWithEmptyBody", "UNUSED_EXPRESSION")

package com.example.newsappmvvm.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.R
import com.example.newsappmvvm.data.model.LocalArticle
import com.example.newsappmvvm.ui.fragment.favorite.adapter.FavoriteAdapter
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_explore.view.*


@SuppressLint("NotifyDataSetChanged")
@BindingAdapter(value = ["app:adapter"])
fun RecyclerView.setAdapter(items: List<LocalArticle>?) {
    this.apply {
        setHasFixedSize(true)
        val adapter = adapter as FavoriteAdapter
        items?.let { adapter.updateData(it) }
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter(value = ["app:uri"])
fun ShapeableImageView.setShapeableImageView(url: String?) {
    url?.let { this.loadImage(it) }
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

@BindingAdapter(value = ["app:textStateConnection"])
fun TextView.setText(text: Int?) {
    this.text = text?.let { resources.getString(it) }
}


@BindingAdapter(value = ["app:src"])
fun ImageView.setImage(img: Int?) {
    img?.let { this.setImageResource(it) }
}

@BindingAdapter(value = ["app:time"])
fun TextView.setTime(time: String?) {
    time?.let { this.text = time.dateToTimeFormat() }
}

@BindingAdapter(value = ["app:text"])
fun TextView.setTexts(text: String?) {
    text?.let { this.text = it }
}

@BindingAdapter(value = ["app:date"])
fun TextView.setDate(date: String?) {
    date?.let { this.text = date.dateFormat() }
}

@BindingAdapter(value = ["show"])
fun setVisibility(view: View, value: Boolean?) {
    value?.let {
        view.visibility = if (value) VISIBLE else GONE
    }
}

@BindingAdapter(value = ["app:setFocus"])
fun setFocus(view: EditText, value: Boolean) {
    view.requestFocus()
    (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}


@BindingAdapter(value = ["app:addTab"])
fun TabLayout.setTabLayout(value: List<Int>?) {
    value?.forEach {
        this.tabLayout.addTab(tabLayout.newTab().setText(resources.getString(it)))
    }
}


@BindingAdapter(value = ["app:loadUrl"])
fun WebView.loadUrl(url: String?) {
    url?.let {
        this.loadUrl(url)
    }
}

fun Context.setToast(message: Int) {
    Toast.makeText(this, resources.getString(message), Toast.LENGTH_SHORT).show()
}
