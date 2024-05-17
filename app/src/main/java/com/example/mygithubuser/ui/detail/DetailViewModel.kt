package com.example.mygithubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygithubuser.data.database.FavoriteUser
import com.example.mygithubuser.data.remote.response.DetailUserResponse
import com.example.mygithubuser.data.remote.response.ItemsItem
import com.example.mygithubuser.data.remote.retrofit.ApiConfig
import com.example.mygithubuser.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val userRepository: UserRepository = UserRepository(application)

    private val _userDetail = MutableLiveData<FavoriteUser>()
    val userDetail: LiveData<FavoriteUser> = _userDetail

    private val _loadingScreen = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loadingScreen

    private val _userFollower = MutableLiveData<List<ItemsItem>>()
    val userFollower: LiveData<List<ItemsItem>> = _userFollower

    private val _userFollowing = MutableLiveData<List<ItemsItem>>()
    val userFollowing: LiveData<List<ItemsItem>> = _userFollowing


    private var isloaded = false
    private var isfollowerloaded = false
    private var isfollowingloaded = false

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetailUser(username: String) {
        if (!isloaded) {
            _loadingScreen.value = true
            val client = ApiConfig.getApiService().getDetailUser(username)
            client.enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>,
                ) {
                    _loadingScreen.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            viewModelScope.launch {
                                val isFavorite = userRepository.getIsFavorite(responseBody.login)
                                val currentUser =
                                    FavoriteUser( // memanggil yang ada di database tanpa menggunakan intent
                                        username = responseBody.login,
                                        name = responseBody.name,
                                        avatarUrl = responseBody.avatarUrl,
                                        followersCount = responseBody.followers.toString(),
                                        followingCount = responseBody.following.toString(),
                                        isFavorite = isFavorite
                                    )
                                _userDetail.postValue(currentUser)
                            }
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _loadingScreen.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            isloaded = true
        }
    }

    fun getUserFollowings(username: String) {
        if (!isfollowingloaded) {
            _loadingScreen.value = true
            val client = ApiConfig.getApiService().getUserFollowing(username)
            client.enqueue(object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    _loadingScreen.value = false
                    if (response.isSuccessful) {
                        _userFollowing.postValue(response.body())
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _loadingScreen.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            isfollowingloaded = true
        }
    }

    fun getUserFollowers(username: String) {
        if (!isfollowerloaded) {
            _loadingScreen.value = true
            val client = ApiConfig.getApiService().getUserFollowers(username)
            client.enqueue(object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    _loadingScreen.value = false
                    if (response.isSuccessful) {
                        _userFollower.postValue(response.body())

                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _loadingScreen.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            isfollowerloaded = true
        }
    }

    fun insertFavorite(favoriteUser: FavoriteUser) {
        userRepository.insert(favoriteUser)
    }
    fun deleteFavorite(favoriteUser: FavoriteUser) {
        userRepository.delete(favoriteUser)
    }

}