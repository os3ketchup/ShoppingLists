package uz.os3ketchup.shoppinglists.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.os3ketchup.shoppinglists.data.ShopListRepositoryImpl
import uz.os3ketchup.shoppinglists.domain.EditShopItemUseCase
import uz.os3ketchup.shoppinglists.domain.GetShopItemUseCase
import uz.os3ketchup.shoppinglists.domain.GetShopListUseCase
import uz.os3ketchup.shoppinglists.domain.RemoveShopItemUseCase
import uz.os3ketchup.shoppinglists.domain.ShopItem

class MainViewModel:ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()



    fun removeShopItem(item: ShopItem){
        removeShopItemUseCase.removeShopItem(item)


    }

    fun editShopItem(item: ShopItem){
        editShopItemUseCase.editShopItem(item.copy(enabled = !item.enabled))

    }

}