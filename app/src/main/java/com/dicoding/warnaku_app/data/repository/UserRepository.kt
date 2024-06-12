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
            emit(FetchResult.Error(error.msg))
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
            emit(FetchResult.Error(error.msg))
        } catch (e: Exception) {
            emit(FetchResult.Error(e.message.toString()))
        } finally {
            _isLoading.postValue(false)
        }
    }

    suspend fun saveToken(token: String) = userPreference.saveToken(token)

    suspend fun loginPref() = userPreference.loginPref()

    fun getLoginStatus() = userPreference.getLoginStatus()
//
//    fun uploadStory(
//        context: Context,
//        file: File?,
//        description: String,
//        lat: Float? = null,
//        lon: Float? = null
//    ): LiveData<FetchResult<UploadStoryResponse>> = liveData {
//        emit(FetchResult.Loading)
//        try {
//            val token = runBlocking { userPreference.getToken().first() }
//            apiService = ApiConfig.getApiService(token.toString())
//            if (file != null) {
//                val files = file.reduceFileImage()
//                val desc = description.toRequestBody("text/plain".toMediaType())
//                val imageFile = files.asRequestBody("image/jpeg".toMediaType())
//                val imageMultipart = MultipartBody.Part.createFormData("photo", files.name, imageFile)
//                val response = apiService.uploadStory(imageMultipart, desc, lat, lon)
//                emit(FetchResult.Success(response))
//            } else {
//                emit(FetchResult.Error(context.getString(R.string.empty_image)))
//            }
//        } catch (e: HttpException) {
//            val response = e.response()?.errorBody()?.string()
//            val error = Gson().fromJson(response, UploadStoryResponse::class.java)
//            emit(FetchResult.Error(error.message))
//        } catch (e: Exception) {
//            emit(FetchResult.Error(e.message.toString()))
//        }
//    }
//
//    suspend fun logout() = userPreference.logout()
//
//    fun getStoryPaging(): LiveData<PagingData<ListStoryItem>> {
//        return liveData {
//            val localCoroutineScope = CoroutineScope(Dispatchers.IO)
//
//            val tokenResult = try {
//                userPreference.getToken().first()
//            } catch (e: Exception) {
//                null
//            }
//
//            if (tokenResult != null) {
//                emit(PagingData.empty())
//                val token = tokenResult
//                try {
//                    apiService = ApiConfig.getApiService(token.toString())
//                    val pager = Pager(
//                        config = PagingConfig(
//                            pageSize = 5
//                        ),
//                        pagingSourceFactory = {
//                            StoryPagingSource(apiService)
//                        }
//                    )
//                    val pagingData = pager.flow.cachedIn(localCoroutineScope)
//                    pagingData.collect { data ->
//                        emit(data)
//                    }
//                } catch (e: HttpException) {
//
//                } catch (e: Exception) {
//
//                }
//            } else {
//
//            }
//        }
//    }
//
//
//    fun getStoriesWithLocation(): LiveData<FetchResult<List<ListStoryItem>>> = liveData {
//        emit(FetchResult.Loading)
//        try {
//            val token = runBlocking {
//                userPreference.getToken().first()
//            }
//            val apiService = ApiConfig.getApiService(token.toString())
//            val response = apiService.getStoriesWithLocation()
//
//            val stories: List<ListStoryItem>? = response.listStory?.filterNotNull()
//
//            if (stories != null) {
//                emit(FetchResult.Success(stories))
//            } else {
//                emit(FetchResult.Error("No stories found"))
//            }
//
//        } catch (e: HttpException) {
//            val jsonInString = e.response()?.errorBody()?.string()
//            val errorBody = Gson().fromJson(jsonInString, StoryResponse::class.java)
//            emit(FetchResult.Error(errorBody.message))
//        } catch (e: Exception) {
//            Log.d("StoryRepository", "getAllStoriesWithLocation: ${e.message.toString()}")
//            emit(FetchResult.Error(e.message.toString()))
//        }
//    }
//
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
}