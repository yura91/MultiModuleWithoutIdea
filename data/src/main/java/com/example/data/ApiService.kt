package com.example.data

import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getUsers(): List<ApiUser>

    @GET("more-users")
    fun getMoreUsers(): List<ApiUser>

    @GET("error")
    fun getUsersWithError(): List<ApiUser>
}