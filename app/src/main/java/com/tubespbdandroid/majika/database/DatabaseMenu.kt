package com.tubespbdandroid.majika.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tubespbdandroid.majika.data.RestaurantMenu
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class DatabaseMenu constructor(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val currency: String,
    val price: Int,
    val sold: Int,
    val type: String,
    var qty: Int
)

fun ArrayList<DatabaseMenu>.asDomainModel(): ArrayList<RestaurantMenu> {
    val list = map {
        RestaurantMenu(
            name = it.name,
            description = it.description,
            currency = it.currency,
            price = it.price,
            sold = it.sold,
            type = it.type
        )
    }
    return ArrayList(list)
}