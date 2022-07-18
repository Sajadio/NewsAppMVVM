package com.example.newsappmvvm.data.local.dao

import androidx.room.*
import com.example.newsappmvvm.data.model.PageKeys

@Dao
interface PageKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPageKeys(remoteKeys: List<PageKeys>)

    @Query("SELECT * FROM PAGE_KEY_TABLE WHERE id LIKE :id")
    suspend fun fetchRemoteKeys(id: Int): PageKeys

    @Query("DELETE FROM PAGE_KEY_TABLE")
    suspend fun deleteAllRemoteKeys()
}