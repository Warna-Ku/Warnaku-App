package com.dicoding.warnaku_app.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.warnaku_app.api.response.AnalysisReportsItem
import com.dicoding.warnaku_app.api.response.Customer
import com.dicoding.warnaku_app.data.UserPreference
import com.dicoding.warnaku_app.data.repository.CustomerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HistoryViewModel(private val repository: CustomerRepository, private val userPreference: UserPreference) : ViewModel() {

    private val _analysisReports = MutableLiveData<List<AnalysisReportsItem?>>()
    val analysisReports: LiveData<List<AnalysisReportsItem?>>
        get() = _analysisReports

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    fun fetchAnalysisReports() {
        viewModelScope.launch {
            try {
                val uid = userPreference.getUID().first() ?: ""
                val response = repository.getAnalysisReports(uid)
                if (response.error == false && response.analysisReports != null) {
                    _analysisReports.value = response.analysisReports
                } else {
                    _error.value = response.message
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}