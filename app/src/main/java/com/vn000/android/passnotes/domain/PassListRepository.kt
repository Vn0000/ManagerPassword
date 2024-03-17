package com.vn000.android.passnotes.domain

import androidx.lifecycle.LiveData

interface PassListRepository {
    suspend fun addPassItem(passItem: PassItem)
    suspend fun deletePassItem (passItem: PassItem)
    suspend fun getPassItem(passItemId: Long): PassItem
    fun getPassList(): LiveData<List<PassItem>>
}