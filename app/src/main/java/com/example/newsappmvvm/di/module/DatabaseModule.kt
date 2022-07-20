package com.example.newsappmvvm.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.newsappmvvm.data.local.AppDB
import com.example.newsappmvvm.data.local.dao.ArticleDao
import com.example.newsappmvvm.data.repository.RepositoryImpl
import com.example.newsappmvvm.utils.NAME_DATABASE
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDB(context: Context) = Room.databaseBuilder(context, AppDB::class.java, NAME_DATABASE).build()

}