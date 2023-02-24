package com.tubespbdandroid.majika.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.tubespbdandroid.majika.database.DatabaseMenu
import com.tubespbdandroid.majika.database.getDatabase
import com.tubespbdandroid.majika.repository.MenusRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MenuViewModel(application: Application): AndroidViewModel(application) {
    private val menusRepository = MenusRepository(getDatabase(application))
    val menus = menusRepository.menus
    val foods = menusRepository.foods
    val drinks = menusRepository.drinks
    val menuOnCart = menusRepository.menuOnCart

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository(){
        viewModelScope.launch {
            try {
                menusRepository.fetchMenus()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                if (menus.value.isNullOrEmpty()) {
                    _eventNetworkError.value = true
                }
            }
        }
    }

    private fun addQuantity(databaseMenu: DatabaseMenu) {
        databaseMenu.qty+=1
        viewModelScope.launch {
            menusRepository.updateQuantity(databaseMenu)
        }
    }

    private fun reduceQuantity(databaseMenu: DatabaseMenu) {
        if (databaseMenu.qty != 0) {
            databaseMenu.qty -= 1
            viewModelScope.launch {
                menusRepository.updateQuantity(databaseMenu)
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MenuViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
