package com.dicoding.storyapp.di

import android.content.Context
import com.dicoding.warnaku_app.api.ApiConfig
import com.dicoding.warnaku_app.data.UserPreference
import com.dicoding.warnaku_app.data.dataStore
import com.dicoding.warnaku_app.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getToken().first() }
        val apiService = ApiConfig.getApiService(user.toString())
        return UserRepository.getInstance(apiService, pref)
    }
}