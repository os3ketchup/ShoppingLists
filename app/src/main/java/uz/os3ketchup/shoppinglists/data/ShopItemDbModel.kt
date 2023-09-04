package uz.os3ketchup.shoppinglists.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.os3ketchup.shoppinglists.domain.ShopItem

@Entity(tableName = "shop_items")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val count:Int,
    val enabled:Boolean
)