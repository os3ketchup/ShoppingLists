package uz.os3ketchup.shoppinglists.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.os3ketchup.shoppinglists.domain.ShopItem
import uz.os3ketchup.shoppinglists.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {
    private var shopListLD = MutableLiveData<List<ShopItem>>()
    private var shopList = sortedSetOf<ShopItem>({ it, it2 ->
        it.id.compareTo(it2.id)
    })
    private var incrementShopItemId = 0

    init {
        for (i in 0 until 20) {
            val item = ShopItem("name $i", i, Random.nextBoolean())
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = incrementShopItemId++
        }
        shopList.add(shopItem)
        updateShopList()
    }


    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find {
            it.id == id
        } ?: throw RuntimeException("shop item which ID is $id not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {

        return shopListLD
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateShopList()
    }

    private fun updateShopList() {
        shopListLD.value = shopList.toList()
    }
}