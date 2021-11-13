package com.revelatestudio.revelatemountain.util

sealed class Resource<T>(val data: T?, val message : String?) {

    class Success<T>(data: T) : Resource<T>(data, null)
    class Error<T>(msg : String) : Resource<T>(null, msg)
    class Empty<T>() : Resource<T>(null,null)

}