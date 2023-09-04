package uz.os3ketchup.shoppinglists.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun editShopItem(shopItem: ShopItem)
    suspend fun getShopItem(id: Int): ShopItem
    fun getShopList(): LiveData<List<ShopItem>>
    suspend fun removeShopItem(shopItem: ShopItem)
}