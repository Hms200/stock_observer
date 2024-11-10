package com.hms.so.configuration

data class ResponseObject<T>(
    val success: Boolean,
    val body: T? = null,
    val errorMessage: String? = null,
)
