package com.vn000.android.passnotes.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.UUID

@Dao
interface PassListDao {

    @Query("SELECT * FROM pass_items")
    fun getPassList(): LiveData<List<PassItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPassItem(passItemDbModel: PassItemDbModel)

    @Query("DELETE FROM pass_items WHERE id=:passItemId")
    suspend fun deletePassItem(passItemId: Long)

    @Query("SELECT * FROM pass_items WHERE id=:passItemId LIMIT 1")
    suspend fun getPassItem(passItemId: Long): PassItemDbModel
}
