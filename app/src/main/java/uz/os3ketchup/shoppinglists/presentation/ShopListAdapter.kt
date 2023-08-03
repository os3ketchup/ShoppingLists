package uz.os3ketchup.shoppinglists.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.os3ketchup.shoppinglists.R
import uz.os3ketchup.shoppinglists.databinding.ItemShopDisabledBinding
import uz.os3ketchup.shoppinglists.databinding.ItemShopEnabledBinding
import uz.os3ketchup.shoppinglists.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter() : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {


    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            SHOP_ITEM_ENABLED -> R.layout.item_shop_enabled
            SHOP_ITEM_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("unknown view type:$viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false)
        return ShopItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding

        when(binding){
            is ItemShopEnabledBinding->{
                binding.shopItem = shopItem
            }
            is ItemShopDisabledBinding->{
                binding.shopItem = shopItem
            }
        }



        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        binding.root.setOnClickListener {
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