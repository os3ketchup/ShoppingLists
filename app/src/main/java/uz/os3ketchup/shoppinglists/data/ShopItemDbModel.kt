package uz.os3ketchup.shoppinglists.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.os3ketchup.shoppinglists.domain.ShopItem
import javax.inject.Inject

@Entity(tableName = "shop_items")
data class ShopItemDbModel @Inject constructor (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val count:Int,
    val enabled:Boolean
)