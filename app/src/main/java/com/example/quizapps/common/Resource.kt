package com.example.quizapps.common

sealed class Resource<out T>(data : T?=null,error : String?=null) {
     data  object Loading : Resource<Nothing>()
    data class Success<T>(val data : T?) : Resource<T>(data = data)
    data class Error<T>(val error : String?):Resource<T>(error = error, data = null)

}