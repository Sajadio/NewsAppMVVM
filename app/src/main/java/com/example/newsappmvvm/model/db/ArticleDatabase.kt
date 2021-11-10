package com.example.newsappmvvm.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsappmvvm.model.domen.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Convert::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).apply { instance }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}