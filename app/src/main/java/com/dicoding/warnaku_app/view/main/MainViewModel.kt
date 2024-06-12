package com.dicoding.warnaku_app.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.dicoding.warnaku_app.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {
//    fun logout() {
//        viewModelScope.launch {
//            repository.logout()
//        }
//    }

    fun getLoginStatus() = repository.getLoginStatus()
}