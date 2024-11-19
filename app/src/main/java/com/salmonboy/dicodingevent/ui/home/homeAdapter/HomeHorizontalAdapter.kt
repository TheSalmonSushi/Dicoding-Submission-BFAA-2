package com.salmonboy.dicodingevent.ui.home.homeAdapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salmonboy.dicodingevent.data.local.entity.EventEntity
import com.salmonboy.dicodingevent.databinding.ItemDicodingEventBinding
import com.salmonboy.dicodingevent.ui.detail.EventDetailActivity


class HomeHorizontalAdapter(

) : ListAdapter<EventEntity, HomeHorizontalAdapter.ViewHolder>(DIFF_CALLBACK) {

     class ViewHolder(private val binding: ItemDicodingEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            binding.tvDicodingEventName.text = event.name
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.ivDicodingEvent)
            itemView.setOnClickListener{
                val intent = Intent(itemView.context, EventDetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id.toString())
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemDicodingEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)

    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<EventEntity> =
            object : DiffUtil.ItemCallback<EventEntity>() {
                override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: EventEntity,
                    newItem: EventEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}