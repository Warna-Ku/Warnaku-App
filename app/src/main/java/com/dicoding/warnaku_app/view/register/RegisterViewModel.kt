package com.dicoding.warnaku_app.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.warnaku_app.api.response.RegisterResponse
import com.dicoding.warnaku_app.data.FetchResult
import com.dicoding.warnaku_app.data.repository.UserRepository

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<FetchResult<RegisterResponse>> {
        return repository.register(name, email, password)
    }
}