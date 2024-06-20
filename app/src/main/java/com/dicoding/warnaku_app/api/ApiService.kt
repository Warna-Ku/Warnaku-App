package com.dicoding.warnaku_app.api

import com.dicoding.warnaku_app.api.response.CustomersResponse
import com.dicoding.warnaku_app.api.response.AnalysisResponse
import com.dicoding.warnaku_app.api.response.Customer
import com.dicoding.warnaku_app.api.response.LoginResponse
import com.dicoding.warnaku_app.api.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    //register
    @FormUrlEncoded
    @POST("users")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("customers/history")
    suspend fun getCustomers(
        @Field("workerID") workerID: String
    ): CustomersResponse


    @POST("/image-analyze")
    suspend fun getAnalysis(
        @Field("workerID") workerID: String,
        @Field("customerID") customerID: String,
        @Field("image") image: String
    ): AnalysisResponse

    @POST("/customers")
    suspend fun createCustomer(
        @Body customer: Customer
    ): CustomersResponse
}