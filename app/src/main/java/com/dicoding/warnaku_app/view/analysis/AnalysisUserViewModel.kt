package com.dicoding.warnaku_app.view.analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.warnaku_app.api.response.CustomersResponse
import com.dicoding.warnaku_app.data.UserPreference
import com.dicoding.warnaku_app.data.repository.CustomerRepository
import kotlinx.coroutines.launch

class AnalysisUserViewModel(
    private val repository: CustomerRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    private val _customerResponse = MutableLiveData<CustomersResponse>()
    val customerResponse: LiveData<CustomersResponse> get() = _customerResponse

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun createCustomer(fullname: String, phone: String, address: String, email: String) {
        viewModelScope.launch {
            try {
                val response = repository.createCustomer(fullname, phone, address, email)
                if (response.error == true) {
                    _error.value = response.message
                } else {
                    _customerResponse.value = response
                    response.data?.let {
                        it.customerID?.let { it1 -> saveCustomerID(it1) }
                    }
                    _error.value = null
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    private fun saveCustomerID(customerID: Int) {
        viewModelScope.launch {
            repository.saveCustomerID(customerID)
        }
    }
}