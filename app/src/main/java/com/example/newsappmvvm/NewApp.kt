package com.example.newsappmvvm

import android.app.Application
import com.example.newsappmvvm.di.component.AppComponent
import com.example.newsappmvvm.di.component.DaggerAppComponent
import com.example.newsappmvvm.utils.BASE_URL

class NewsApp:Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).baseUrl(BASE_URL).build()
    }


}