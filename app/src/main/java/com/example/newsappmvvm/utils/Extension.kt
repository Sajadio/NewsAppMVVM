package com.example.newsappmvvm.utils

import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).placeholder(randomColor.random()).into(this)
}
