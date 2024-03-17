package com.vn000.android.passnotes.presentation.listpass

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vn000.android.passnotes.databinding.ItemPassBinding
import com.vn000.android.passnotes.domain.PassItem

class PassListAdapter :
    ListAdapter<PassItem, PassListAdapter.PassItemViewHolder>(PassItemDiffCalback()) {

    var onPassItemClickListener: ((PassItem) -> Unit)? = null


    override fun onBindViewHolder(holder: PassItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassItemViewHolder {
        return PassItemViewHolder(
            ItemPassBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    inner class PassItemViewHolder(private val binding: ItemPassBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var passItem: PassItem
        private val tvTitlePass: TextView = binding.tvTitlePass
        private val imIconPass: ImageView = binding.imIconPass


        fun bind(passItem: PassItem) {
            this.passItem = passItem

            tvTitlePass.text = this.passItem.name
            imIconPass.load(passItem.iconUrl)

            binding.root.setOnClickListener {
                onPassItemClickListener?.invoke(passItem)
            }
        }
    }
}