package uz.os3ketchup.shoppinglists.domain

import javax.inject.Inject

class RemoveShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {
    suspend fun removeShopItem(shopItem: ShopItem) {
        shopListRepository.removeShopItem(shopItem)
    }
}