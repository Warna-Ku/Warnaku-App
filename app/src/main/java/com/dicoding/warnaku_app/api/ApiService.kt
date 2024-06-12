package com.dicoding.warnaku_app.api

import com.dicoding.warnaku_app.api.response.LoginResponse
import com.dicoding.warnaku_app.api.response.RegisterResponse
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
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
//
//    @GET("stories")
//    suspend fun getStories(
//        @Header("Authorization") token: String? = null
//    ): StoryResponse
//
//    @Multipart
//    @POST("stories")
//    suspend fun uploadStory(
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//        @Part("lat") lat: Float?,
//        @Part("lon") lon: Float?
//    ): UploadStoryResponse

}