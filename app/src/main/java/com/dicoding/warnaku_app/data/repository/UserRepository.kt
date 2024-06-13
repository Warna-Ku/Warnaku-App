package com.dicoding.warnaku_app.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.warnaku_app.api.ApiService
import com.dicoding.warnaku_app.api.response.LoginResponse
import com.dicoding.warnaku_app.api.response.RegisterResponse
import com.dicoding.warnaku_app.data.FetchResult
import com.dicoding.warnaku_app.data.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private var apiService: ApiService
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<FetchResult<RegisterResponse>> = liveData {
        emit(FetchResult.Loading)
        _isLoading.postValue(true)
        try {
            val response = apiService.register(name, email, password)
            emit(FetchResult.Success(response))
        } catch (e: HttpException) {
            val response = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(response, RegisterResponse::class.java)
            emit(FetchResult.Error(error.message))
        } catch (e: Exception) {
            emit(FetchResult.Error(e.message.toString()))
        } finally {
            _isLoading.postValue(false)
        }
    }

    fun login(email: String, password: String): LiveData<FetchResult<LoginResponse>> = liveData {
        emit(FetchResult.Loading)
        _isLoading.postValue(true)
        try {
            val response = apiService.login(email, password)
            emit(FetchResult.Success(response))
        } catch (e: HttpException) {
            val response = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(response, LoginResponse::class.java)
            emit(FetchResult.Error(error.message))
        } catch (e: Exception) {
            emit(FetchResult.Error(e.message.toString()))
        } finally {
            _isLoading.postValue(false)
        }
    }

    suspend fun saveToken(token: String) = userPreference.saveToken(token)

    suspend fun loginPref() = userPreference.loginPref()

    fun getLoginStatus() = userPreference.getLoginStatus()

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService, preferences: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(preferences, apiService).also {
                    instance = it
                }
            }
    }
    suspend fun logout() = userPreference.logout()
}