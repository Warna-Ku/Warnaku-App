package com.dicoding.warnaku_app.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.warnaku_app.api.response.LoginResponse
import com.dicoding.warnaku_app.data.FetchResult
import com.dicoding.warnaku_app.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel() {
    val isLoading: LiveData<Boolean> = repository.isLoading

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    fun login(email: String, password: String): LiveData<FetchResult<LoginResponse>> {
        return repository.login(email, password)
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            repository.saveToken(token)
            repository.loginPref()
        }
    }

    fun saveUID(uid: String) {
        viewModelScope.launch {
            repository.saveUID(uid)
            repository.loginPref()
        }
    }

    fun updateUserName(name: String) {
        _userName.value = name
    }
}