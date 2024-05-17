package com.example.mygithubuser.ui.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.databinding.ActivityFavoriteUserBinding
import com.example.mygithubuser.ui.main.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()
        supportActionBar?.title = "Favorite Users"

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserFavorite.layoutManager = layoutManager

        favoriteUserViewModel.getAllFavoriteUsers().observe(this) { favoriteUsers ->
            val adapter = FavoriteUserAdapter()
            adapter.submitList(favoriteUsers.sortedBy {it.name})
            binding.rvUserFavorite.adapter = adapter
        }
    }
}