package com.tubespbdandroid.majika.data

import com.google.gson.annotations.SerializedName

data class DefaultResponse<T> (
    @SerializedName("data")
    val data: ArrayList<T>
)