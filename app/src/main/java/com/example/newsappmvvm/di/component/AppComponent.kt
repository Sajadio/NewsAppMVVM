package com.example.newsappmvvm.di.component

import android.app.Application
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.di.module.DatabaseModule
import com.example.newsappmvvm.di.module.NetworkModule
import com.example.newsappmvvm.ui.activity.NewsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    @OptIn(ExperimentalPagingApi::class)
    fun inject(newsActivity: NewsActivity)

    @Component.Builder
    interface Builder {

        fun application(@BindsInstance context:Context): Builder

        @BindsInstance
        fun baseUrl(@Named("baseUrl") baseUrl: String): Builder

        fun build(): AppComponent
    }

}