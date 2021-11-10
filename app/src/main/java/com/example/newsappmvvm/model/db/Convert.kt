package com.example.newsappmvvm.model.db

import androidx.room.TypeConverter
import com.example.newsappmvvm.model.domen.Source

class Convert {
    @TypeConverter
    fun from(source: Source) = source.name.toString()

    @TypeConverter
    fun to(name: String) = Source(name, name)
}