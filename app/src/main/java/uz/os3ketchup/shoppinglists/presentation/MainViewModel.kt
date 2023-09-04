package uz.os3ketchup.shoppinglists.presentation

import android.app.Application
import androidx.annotation.RestrictTo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import uz.os3ketchup.shoppinglists.data.ShopListRepositoryImpl
import uz.os3ketchup.shoppinglists.domain.EditShopItemUseCase
import uz.os3ketchup.shoppinglists.domain.GetShopItemUseCase
import uz.os3ketchup.shoppinglists.domain.GetShopListUseCase
import uz.os3ketchup.shoppinglists.domain.RemoveShopItemUseCase
import uz.os3ketchup.shoppinglists.domain.ShopItem

class MainViewModel(application: Application):AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()



    fun removeShopItem(item: ShopItem){
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(item)
        }



    }

    fun editShopItem(item: ShopItem){
        viewModelScope.launch {
            editShopItemUseCase.editShopItem(item.copy(enabled = !item.enabled))
        }

    }

}