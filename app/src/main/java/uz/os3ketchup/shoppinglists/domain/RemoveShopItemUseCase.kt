package uz.os3ketchup.shoppinglists.domain

class RemoveShopItemUseCase(private val shopListRepository:ShopListRepository) {
    suspend  fun removeShopItem(shopItem: ShopItem){
        shopListRepository.removeShopItem(shopItem)
    }
}