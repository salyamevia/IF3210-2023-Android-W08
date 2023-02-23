package com.tubespbdandroid.majika.retrofit

import com.tubespbdandroid.majika.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BranchService {
    val BASE_URL:String = Config.BASE_URL.value // Android localhost
    val endpoint: BranchAPI

    get() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(BranchAPI::class.java)
    }
}