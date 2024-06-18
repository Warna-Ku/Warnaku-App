package com.dicoding.warnaku_app.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.warnaku_app.api.response.Customer
import com.dicoding.warnaku_app.data.UserPreference
import com.dicoding.warnaku_app.data.repository.CustomerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HistoryViewModel(private val repository: CustomerRepository, private val userPreference: UserPreference) : ViewModel() {

    private val _customers = MutableLiveData<List<Customer?>>()
    val customers: LiveData<List<Customer?>>
        get() = _customers

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    fun fetchCustomers() {
        viewModelScope.launch {
            try {
                val uid = userPreference.getUID().first() ?: ""
                val response = repository.getCustomers(uid)
                if (response.error == false && response.analysisReports != null) {
                    _customers.value = response.analysisReports.map { it?.customer }
                } else {
                    _error.value = response.message
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}