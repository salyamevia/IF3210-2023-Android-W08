package com.tubespbdandroid.majika.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tubespbdandroid.majika.data.DefaultResponse
import com.tubespbdandroid.majika.data.RestaurantMenu
import com.tubespbdandroid.majika.database.DatabaseMenu
import com.tubespbdandroid.majika.database.MenusDatabase
import com.tubespbdandroid.majika.database.asDomainModel
import com.tubespbdandroid.majika.retrofit.menus.MenusClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID

class MenusRepository(private val db: MenusDatabase) {
    val menus: LiveData<ArrayList<RestaurantMenu>> = Transformations.map(db.menuDao.getAllMenus()) {
        it.asDomainModel()
    }
    val foods: LiveData<ArrayList<RestaurantMenu>> = Transformations.map(db.menuDao.getAllFoods()) {
        it.asDomainModel()
    }
    val drinks: LiveData<ArrayList<RestaurantMenu>> = Transformations.map(db.menuDao.getAllDrinks()) {
        it.asDomainModel()
    }
    val menuOnCart: LiveData<ArrayList<RestaurantMenu>> = Transformations.map(db.menuDao.getCartItem()){
        it.asDomainModel()
    }

    suspend fun fetchMenus() {
        val menusCall = MenusClient.service.getAllMenus()

        menusCall.enqueue(object: Callback<DefaultResponse<RestaurantMenu>> {
            override fun onResponse(
                call: Call<DefaultResponse<RestaurantMenu>>,
                response: Response<DefaultResponse<RestaurantMenu>>
            ) {
                var menus = response.body()?.data?.map {
                    DatabaseMenu(
                        id = UUID.randomUUID().toString(),
                        name = it.name,
                        description = it.description,
                        currency = it.currency,
                        price = it.price,
                        sold = it.sold,
                        type = it.type,
                        qty = 0
                    )
                }

                menus = ArrayList(menus)

                db.menuDao.insertAll(menus)
            }

            override fun onFailure(call: Call<DefaultResponse<RestaurantMenu>>, t: Throwable) {
                println("ERROR OCCURED ON MenusRepository FILE: ${t.message}")
            }
        })
    }

    suspend fun updateQuantity(newDatabaseMenu: DatabaseMenu) {
        withContext(Dispatchers.IO) {
            db.menuDao.updateQuantity(newDatabaseMenu)
        }
    }
}