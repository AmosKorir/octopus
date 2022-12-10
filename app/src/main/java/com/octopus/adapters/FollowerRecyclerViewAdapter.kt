package com.octopus.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.octopus.R
import com.octopus.databinding.FollowerItemLayoutBinding
import com.octopus.domain.models.Follower


class FollowerRecyclerViewAdapter(val context: Context, private val followers: List<Follower>) :
    RecyclerView.Adapter<FollowerRecyclerViewAdapter.FollowerViewHolder>() {


    class FollowerViewHolder(val binding: FollowerItemLayoutBinding) : ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding =
            FollowerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val follower = followers[position]
        holder.binding.followerName.text = follower.login
        Glide
            .with(context)
            .load(follower.avatar_url)
            .centerCrop()
            .circleCrop()
            .placeholder(R.drawable.octocat)
            .into(holder.binding.followerImage);
    }

    override fun getItemCount(): Int {
        return followers.size
    }
}