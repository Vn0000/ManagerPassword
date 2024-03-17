package com.vn000.android.passnotes.presentation.listpass

import androidx.recyclerview.widget.DiffUtil
import com.vn000.android.passnotes.domain.PassItem

class PassItemDiffCalback: DiffUtil.ItemCallback<PassItem>() {
    override fun areItemsTheSame(oldItem: PassItem, newItem: PassItem): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PassItem, newItem: PassItem): Boolean {
        return  oldItem == newItem
    }
}