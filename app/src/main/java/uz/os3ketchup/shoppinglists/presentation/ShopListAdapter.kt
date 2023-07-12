package uz.os3ketchup.shoppinglists.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.os3ketchup.shoppinglists.R
import uz.os3ketchup.shoppinglists.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter() : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {


    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = when (viewType) {
            SHOP_ITEM_ENABLED -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shop_enabled, parent, false)

            SHOP_ITEM_DISABLED -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shop_disabled, parent, false)

            else -> throw RuntimeException("unknown view type:$viewType")
        }
        return ShopItemViewHolder(view)
    }


    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()

        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }


    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            SHOP_ITEM_ENABLED
        } else {
            SHOP_ITEM_DISABLED
        }

    }

    companion object {
        const val SHOP_ITEM_ENABLED = 100
        const val SHOP_ITEM_DISABLED = 200
        const val MAX_POOL = 30
    }
}