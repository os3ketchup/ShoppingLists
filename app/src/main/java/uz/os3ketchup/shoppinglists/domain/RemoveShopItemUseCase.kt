package uz.os3ketchup.shoppinglists.domain

class RemoveShopItemUseCase(private val shopListRepository:ShopListRepository) {
    fun removeShopItem(shopItem: ShopItem){
        shopListRepository.removeShopItem(shopItem)
    }
}