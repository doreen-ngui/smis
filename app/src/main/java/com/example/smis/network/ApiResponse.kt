package com.example.smis.network

data class ApiResponse<T> (
    val success: Boolean,
    val data: T?,
    val message: String?
    )