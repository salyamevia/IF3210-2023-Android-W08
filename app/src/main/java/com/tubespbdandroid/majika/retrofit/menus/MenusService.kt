package com.tubespbdandroid.majika.retrofit.menus

import com.tubespbdandroid.majika.data.DefaultResponse
import com.tubespbdandroid.majika.data.RestaurantMenu
import retrofit2.Call
import retrofit2.http.GET

interface MenusService {
    @GET("menu")
    fun getAllMenus(): Call<DefaultResponse<RestaurantMenu>>

    @GET("menu/food")
    fun getAllFoods(): Call<DefaultResponse<RestaurantMenu>>

    @GET("menu/drink")
    fun getAllDrinks(): Call<DefaultResponse<RestaurantMenu>>
}