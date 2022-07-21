package com.example.newsappmvvm.data.mapper

interface MapperNews<I, O> {
    fun map(input: I): O
}