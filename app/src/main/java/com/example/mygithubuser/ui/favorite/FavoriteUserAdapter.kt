package com.example.mygithubuser.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubuser.data.database.FavoriteUser
import com.example.mygithubuser.databinding.ItemUserBinding
import com.example.mygithubuser.ui.detail.DetailActivity

class FavoriteUserAdapter: ListAdapter<FavoriteUser, FavoriteUserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemName: FavoriteUser) {
            binding.tvItemName.text = itemName.username
            Glide.with(binding.root)
                .load(itemName.avatarUrl)
                .into(binding.imgUserPhoto)
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, DetailActivity::class.java)
                intentDetail.putExtra("ID", itemName.name)
                intentDetail.putExtra("USERNAME", itemName.username)
                intentDetail.putExtra("AVATAR", itemName.avatarUrl)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}