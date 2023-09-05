package uz.os3ketchup.shoppinglists.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import uz.os3ketchup.shoppinglists.domain.ShopItem
import uz.os3ketchup.shoppinglists.domain.ShopListRepository
import javax.inject.Inject


class ShopListRepositoryImpl @Inject constructor(
    private val mapper: ShopListMapper,
    private val shopListDao: ShopListDao
) : ShopListRepository {


//    private val shopListDao = AppDatabase.getInstance(application).shopListDao()



    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }


    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(id: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> {

        return MediatorLiveData<List<ShopItem>>().apply {

            addSource(shopListDao.getShopList()) {
                value = mapper.mapListDbModelToListEntity(it)
            }
        }
    }

    override suspend fun removeShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

}