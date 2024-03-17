package com.vn000.android.passnotes.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PassItem(
    val name: String? = null,
    val password: String?,
    val url: String?,
    val id: Long = Date().time,
    val iconUrl: String? = null
) : Parcelable
