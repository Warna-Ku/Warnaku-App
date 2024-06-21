package com.dicoding.warnaku_app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.storyapp.di.Injection
import com.dicoding.warnaku_app.data.UserPreference
import com.dicoding.warnaku_app.data.dataStore
import com.dicoding.warnaku_app.data.repository.CustomerRepository
import com.dicoding.warnaku_app.data.repository.UserRepository
import com.dicoding.warnaku_app.view.analysis.AnalysisUserViewModel
import com.dicoding.warnaku_app.view.history.HistoryViewModel
import com.dicoding.warnaku_app.view.login.LoginViewModel
import com.dicoding.warnaku_app.view.main.MainViewModel
import com.dicoding.warnaku_app.view.profile.ProfileViewModel
import com.dicoding.warnaku_app.view.register.RegisterViewModel

class ViewModelFactory(
    private val repository: UserRepository,
    private val historyRepository: CustomerRepository? = null,
    private val userPreference: UserPreference? = null
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) && historyRepository != null && userPreference != null -> {
                HistoryViewModel(historyRepository,userPreference) as T
            }
            modelClass.isAssignableFrom(AnalysisUserViewModel::class.java) && historyRepository != null && userPreference != null  -> {
                AnalysisUserViewModel(historyRepository, userPreference) as T
            }
            modelClass.isAssignableFrom( ProfileViewModel ::class.java) -> {
                ProfileViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                val userRepo = Injection.provideUserRepository(context)
                val historyRepo = Injection.provideHistoryRepository(context)
                val userPref = UserPreference.getInstance(context.dataStore)
                ViewModelFactory(userRepo, historyRepo, userPref).also { INSTANCE = it }
            }
        }
    }
}