package com.dicoding.warnaku_app.data.repository

import com.dicoding.warnaku_app.api.ApiService
import com.dicoding.warnaku_app.api.response.Customer
import com.dicoding.warnaku_app.api.response.CustomersResponse
import com.dicoding.warnaku_app.data.UserPreference

class CustomerRepository(private val preferences: UserPreference, private val apiService: ApiService) {

    suspend fun getCustomers(workerID: String): CustomersResponse {
        return apiService.getCustomers(workerID)
    }

    suspend fun createCustomer(fullname: String, phone: String, address: String, email: String): CustomersResponse {
        val customer = Customer(fullname, phone, address, email)
        return apiService.createCustomer(customer)
    }

    companion object {
        @Volatile
        private var instance: CustomerRepository? = null

        fun getInstance(apiService: ApiService, preferences: UserPreference): CustomerRepository =
            instance ?: synchronized(this) {
                instance ?: CustomerRepository(preferences, apiService).also {
                    instance = it
                }
            }
    }
}