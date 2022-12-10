package com.octopus.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.octopus.R
import com.octopus.databinding.FollowerItemLayoutBinding
import com.octopus.domain.models.Follower

class PagingFollowerAdapter(val  context: Context): PagingDataAdapter<Follower,PagingFollowerAdapter.FollowerViewHolder>(FOLLOWER_DIFF_CALLBACK){
    class FollowerViewHolder(val binding: FollowerItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding =
            FollowerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        getItem(position)?.let { follower ->
            holder.binding.followerName.text = follower.login
            Glide
                .with(context)
                .load(follower.avatar_url)
                .centerCrop()
                .circleCrop()
                .placeholder(R.drawable.octocat)
                .into(holder.binding.followerImage);
        }


    }


    companion object {
        private val FOLLOWER_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Follower>() {
            override fun areItemsTheSame(oldItem: Follower, newItem: Follower): Boolean =
                oldItem.login == newItem.login

            override fun areContentsTheSame(oldItem: Follower, newItem:Follower): Boolean =
                oldItem == newItem
        }
    }

}