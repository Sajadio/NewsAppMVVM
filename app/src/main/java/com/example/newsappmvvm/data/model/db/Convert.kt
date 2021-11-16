package com.example.newsappmvvm.data.model.db

import androidx.room.TypeConverter
import com.example.newsappmvvm.data.model.domen.Source

class Convert {
    @TypeConverter
    fun from(source: Source) = source.name.toString()

    @TypeConverter
    fun to(name: String) = Source(name, name)
}