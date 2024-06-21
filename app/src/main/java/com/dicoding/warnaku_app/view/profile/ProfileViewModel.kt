package com.dicoding.warnaku_app.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.warnaku_app.api.response.LoginResult
import com.dicoding.warnaku_app.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository): ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult?>()
    val loginResult: LiveData<LoginResult?> = _loginResult

    fun loadLoginResult() {
        viewModelScope.launch {
            repository.getLoginResult().collect { result ->
                _loginResult.postValue(result)
            }
        }
    }
}