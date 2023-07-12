package uz.os3ketchup.shoppinglists.presentation

import android.content.ContentValues.TAG
import android.net.wifi.WifiConfiguration.Status.ENABLED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import uz.os3ketchup.shoppinglists.R
import uz.os3ketchup.shoppinglists.domain.ShopItem
import uz.os3ketchup.shoppinglists.presentation.ShopListAdapter.Companion.MAX_POOL
import uz.os3ketchup.shoppinglists.presentation.ShopListAdapter.Companion.SHOP_ITEM_DISABLED
import uz.os3ketchup.shoppinglists.presentation.ShopListAdapter.Companion.SHOP_ITEM_ENABLED

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()
        mainViewModel.shopList.observe(this) {
            Log.d(SOMETHING, "onCreate:s $it")
            shopListAdapter.submitList(it)
            
        }

    }

    private fun setupRecyclerView() {
        val shopListRV = findViewById<RecyclerView>(R.id.recyclerView_main)
        shopListAdapter = ShopListAdapter()
        with(shopListRV){
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(SHOP_ITEM_ENABLED, MAX_POOL)
            recycledViewPool.setMaxRecycledViews(SHOP_ITEM_DISABLED, MAX_POOL)
        }
        setUpLongClickListener()
        setupClickListener()
        setupOnItemSwiped(shopListRV)
    }

    private fun setupOnItemSwiped(shopListRV: RecyclerView?) {
        val itemTouchHelperCallBack = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                mainViewModel.removeShopItem(shopListAdapter.currentList[position])

            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(shopListRV)
    }
    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            Log.d(SOMETHING, "setupRecyclerView: $it")
        }
    }
    private fun setUpLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            mainViewModel.editShopItem(it)
        }
    }
    companion object {
        const val SOMETHING = "LOG ViewModel"

    }

}