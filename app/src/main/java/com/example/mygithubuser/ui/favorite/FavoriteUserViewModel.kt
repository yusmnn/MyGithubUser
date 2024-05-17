package com.example.mygithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.data.database.FavoriteUser
import com.example.mygithubuser.repository.UserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val userRepository: UserRepository = UserRepository(application)
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = userRepository.getAllFavorite()
    init {
        getAllFavoriteUsers()
    }
}