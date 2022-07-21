package com.example.newsappmvvm.utils

import android.app.Activity
import android.icu.text.SimpleDateFormat
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.newsappmvvm.utils.event.Event
import com.example.newsappmvvm.utils.event.EventObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).into(this)
}

fun String.dateToTimeFormat(): String? {
    val prettyTime = PrettyTime(Locale(getCountry()))
    var isTime: String? = null
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    val date: Date = sdf.parse(this)
    isTime = prettyTime.format(date)
    return isTime
}

fun String.dateFormat(): String? {
    val newDate: String?
    val dateFormat = SimpleDateFormat("E, d MMM yyyy", Locale(getCountry()))
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(this)
    newDate = dateFormat.format(date)
    return newDate
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, observer: (T) -> Unit) =
    liveData?.observe(this, Observer(observer))

fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, function: (T) -> Unit) {
    this.observe(owner, EventObserver { it ->
        function(it)
    })
}

fun Window.hideSystemUI(view: View) {
    WindowCompat.setDecorFitsSystemWindows(this, false)
    WindowInsetsControllerCompat(this, view).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}
