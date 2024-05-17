package com.example.mygithubuser.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithubuser.R
import com.example.mygithubuser.data.database.FavoriteUser
import com.example.mygithubuser.databinding.ActivityDetailBinding
import com.example.mygithubuser.ui.main.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("USERNAME")
        val avatar = intent.getStringExtra("AVATAR")

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (username != null) {
            val sectionPagerAdapter = SectionsPagerAdapter(this, username)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f
        }
        if (username != null) {
            detailViewModel.getDetailUser(username)
        }

        detailViewModel.userDetail.observe(this) {
            if (it != null) {
                Glide.with(this@DetailActivity)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(binding.imgUserPhoto)
                binding.tvName.text = it.name
                binding.tvUserName.text = it.username
                binding.tvFollower.text = "${it.followersCount} Follower"
                binding.tvFollowing.text = "${it.followingCount} Following"
                binding.fabAddFavorite.contentDescription = it.isFavorite.toString()

                binding.apply {
                    if (!it.isFavorite) {
                        fabAddFavorite.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@DetailActivity, R.drawable.ic_favorite_border
                            )
                        )
                    } else {
                        fabAddFavorite.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@DetailActivity, R.drawable.ic_favorite
                            )
                        )
                    }
                }
            }
        }
        detailViewModel.loading.observe(this) {
            showLoading(it)
        }

        binding.apply {
            fabAddFavorite.setOnClickListener {
                val userFavorite = FavoriteUser(
                    name = tvName.text.toString(),
                    username = tvUserName.text.toString(),
                    avatarUrl = avatar.toString(),
                    isFavorite = true,
                    followersCount = tvFollower.text.toString(),
                    followingCount = tvFollowing.text.toString()
                )
                Log.e("AVATAR 1: ", avatar.toString())
                val currentIcon = fabAddFavorite.contentDescription
                if (currentIcon == "true") {
                    fabAddFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailActivity, R.drawable.ic_favorite_border
                        )
                    )
                    detailViewModel.deleteFavorite(userFavorite)
                    fabAddFavorite.contentDescription = "false"
                } else {
                    fabAddFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailActivity, R.drawable.ic_favorite
                        )
                    )
                    detailViewModel.insertFavorite(userFavorite)
                    fabAddFavorite.contentDescription = "true"
                }
            }
        }
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}


