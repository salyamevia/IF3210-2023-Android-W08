package com.tubespbdandroid.majika.retrofit.menus

import com.tubespbdandroid.majika.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MenusClient {
    private val BASE_URL:String = Config.BASE_URL.value
    val service: MenusService
        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MenusService::class.java)
        }
}