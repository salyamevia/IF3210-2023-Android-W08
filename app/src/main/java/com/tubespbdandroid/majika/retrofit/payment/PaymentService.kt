package com.tubespbdandroid.majika.retrofit.payment

import com.tubespbdandroid.majika.data.StringQR
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentService {
    @POST("payment/{transaction_id}")
    fun getPaymentStatus(@Path(value = "transaction_id", encoded = true) paymentString: String): Call<StringQR>

    @GET("payment/success")
    fun getSuccessQR(): Call<StringQR>

    @GET("payment/failed")
    fun getFailedQR(): Call<StringQR>
}