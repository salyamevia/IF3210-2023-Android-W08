package com.tubespbdandroid.majika.database

import android.content.Context
import android.provider.ContactsContract.Data
import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.UUID

@Dao
interface MenuDao {
    @Query("SELECT * FROM databasemenu")
    fun getAllMenus(): LiveData<ArrayList<DatabaseMenu>>

    @Query("SELECT * FROM databasemenu WHERE type='Food'")
    fun getAllFoods(): LiveData<ArrayList<DatabaseMenu>>

    @Query("SELECT * FROM databasemenu WHERE type='Drink'")
    fun getAllDrinks(): LiveData<ArrayList<DatabaseMenu>>

    @Query("SELECT * FROM databasemenu WHERE qty>0")
    fun getCartItem(): LiveData<ArrayList<DatabaseMenu>>

    @Query("SELECT * FROM databasemenu WHERE id=:id")
    fun getMenuById(id: String): LiveData<ArrayList<DatabaseMenu>>

    @Update
    fun updateQuantity(newDatabaseMenu: DatabaseMenu): LiveData<ArrayList<DatabaseMenu>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(menus: ArrayList<DatabaseMenu>)
}

@Database(entities = [DatabaseMenu::class], version = 1)
abstract class MenusDatabase: RoomDatabase() {
    abstract val menuDao: MenuDao
}

private lateinit var INSTANCE: MenusDatabase

fun getDatabase(context: Context): MenusDatabase {
    synchronized(MenusDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MenusDatabase::class.java,
                "menus")
                .build()
        }
    }
    return INSTANCE
}