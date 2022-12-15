package com.octopus.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.octopus.R
import com.octopus.databinding.FollowUserItemBinding
import com.octopus.databinding.FollowerItemLayoutBinding
import com.octopus.domain.models.Follower
import com.octopus.domain.models.Following

class HorizontalPagingFollowerAdapter(val context: Context, private val selected: ItemSelected) :
    PagingDataAdapter<Any, HorizontalPagingFollowerAdapter.FollowerViewHolder>(
        FOLLOWER_DIFF_CALLBACK
    ) {
    class FollowerViewHolder(val binding: FollowerItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding =
            FollowerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {

        val item = getItem(position)

        item?.let {
            holder.itemView.setOnClickListener {
                selected.onSelected(item)
            }
            when (it) {
                is Follower -> {
                    holder.binding.followerName.text = it.login
                    Glide
                        .with(context)
                        .load(it.avatar_url)
                        .centerCrop()
                        .circleCrop()
                        .placeholder(R.drawable.octocat)
                        .into(holder.binding.followerImage);
                }

                is Following -> {
                    holder.binding.followerName.text = it.login
                    Glide
                        .with(context)
                        .load(it.avatar_url)
                        .centerCrop()
                        .circleCrop()
                        .placeholder(R.drawable.octocat)
                        .into(holder.binding.followerImage);
                }
                else -> {}
            }
        }


    }


    companion object {
        private val FOLLOWER_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {

                if (oldItem is Follower && newItem is Follower) {
                    return oldItem.id == newItem.id
                }

                if (oldItem is Following && newItem is Following) {
                    return oldItem.id == newItem.id
                }
                return false
            }


            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {

                if (oldItem is Follower && newItem is Follower) {
                    return oldItem == newItem
                }

                if (oldItem is Following && newItem is Following) {
                    return oldItem == newItem
                }

                return false
            }

        }
    }

}