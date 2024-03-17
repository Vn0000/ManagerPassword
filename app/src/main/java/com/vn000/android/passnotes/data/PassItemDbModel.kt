package com.vn000.android.passnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "pass_items")
data class PassItemDbModel(
    @PrimaryKey val id: Long,
    val name: String?,
    val url: String?,
    val password: String?,
    val iconUrl: String? = null
)