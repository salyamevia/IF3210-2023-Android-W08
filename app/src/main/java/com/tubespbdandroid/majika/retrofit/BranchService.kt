package com.tubespbdandroid.majika.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BranchService {
    val BASE_URL:String = "https://127.0.0.1/v1/" // Android localhost
    val endpoint: BranchAPI

    get() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(BranchAPI::class.java)
    }
}