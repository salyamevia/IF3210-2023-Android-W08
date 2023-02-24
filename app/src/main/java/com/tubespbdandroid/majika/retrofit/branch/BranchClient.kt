package com.tubespbdandroid.majika.retrofit.branch

import com.tubespbdandroid.majika.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BranchClient {
    private val BASE_URL:String = Config.BASE_URL.value
    val service: BranchService
    get() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(BranchService::class.java)
    }
}