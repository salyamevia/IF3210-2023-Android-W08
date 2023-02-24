package com.tubespbdandroid.majika.data

data class RestaurantMenu (
    val name: String,
    val description: String,
    val currency: String,
    val price: Int,
    val sold: Int,
    val type: String
    ) {
}