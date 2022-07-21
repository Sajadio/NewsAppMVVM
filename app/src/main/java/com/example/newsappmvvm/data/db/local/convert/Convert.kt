package com.example.newsappmvvm.data.db.local.convert

import androidx.room.TypeConverter
import com.example.newsappmvvm.data.model.Source

class Convert {
    @TypeConverter
    fun from(source: Source) = source.name.toString()

    @TypeConverter
    fun to(name: String) = Source(name, name)
}