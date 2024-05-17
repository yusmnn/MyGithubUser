package com.example.mygithubuser.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mygithubuser.ui.detail.DetailViewModel
import com.example.mygithubuser.ui.favorite.FavoriteUserViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel() as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(mApplication) as T
            modelClass.isAssignableFrom(FavoriteUserViewModel::class.java) -> FavoriteUserViewModel(mApplication) as T
            else -> throw IllegalAccessException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @kotlin.jvm.Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}