package com.example.mygithubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mygithubuser.data.database.FavoriteUser
import com.example.mygithubuser.data.database.FavoriteUserDao
import com.example.mygithubuser.data.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mFavoritesUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoritesUserDao = db.favoriteUserDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavoritesUserDao.getAllFavoriteUsers()

    fun getIsFavorite(username: String): Boolean {
        return mFavoritesUserDao.isFavorite(username)
    }

    fun insert(favorite: FavoriteUser){
        executorService.execute { mFavoritesUserDao.insert(favorite) }
    }

    fun delete(favorite: FavoriteUser) {
        executorService.execute { mFavoritesUserDao.delete(favorite) }
    }
}