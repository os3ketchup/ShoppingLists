package uz.os3ketchup.shoppinglists.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.os3ketchup.shoppinglists.domain.EditShopItemUseCase
import uz.os3ketchup.shoppinglists.domain.GetShopListUseCase
import uz.os3ketchup.shoppinglists.domain.RemoveShopItemUseCase
import uz.os3ketchup.shoppinglists.domain.ShopItem
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase: GetShopListUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
    private val removeShopItemUseCase: RemoveShopItemUseCase
) : ViewModel() {


    val shopList = getShopListUseCase.getShopList()


    fun removeShopItem(item: ShopItem) {
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(item)
        }


    }

    fun editShopItem(item: ShopItem) {
        viewModelScope.launch {
            editShopItemUseCase.editShopItem(item.copy(enabled = !item.enabled))
        }

    }

}