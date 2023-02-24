package com.tubespbdandroid.majika.retrofit.payment

import com.tubespbdandroid.majika.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PaymentClient {
    private val BASE_URL:String = Config.BASE_URL.value
    val service: PaymentService
        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(PaymentService::class.java)
        }
}