package uz.os3ketchup.shoppinglists.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uz.os3ketchup.shoppinglists.R
import uz.os3ketchup.shoppinglists.presentation.ShopItemActivity.Companion.newIntentAddItem
import uz.os3ketchup.shoppinglists.presentation.ShopItemActivity.Companion.newIntentEditItem
import uz.os3ketchup.shoppinglists.presentation.ShopListAdapter.Companion.MAX_POOL
import uz.os3ketchup.shoppinglists.presentation.ShopListAdapter.Companion.SHOP_ITEM_DISABLED
import uz.os3ketchup.shoppinglists.presentation.ShopListAdapter.Companion.SHOP_ITEM_ENABLED

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var addButton: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()
        mainViewModel.shopList.observe(this) {
            Log.d(SOMETHING, "onCreate:s $it")
            shopListAdapter.submitList(it)
        }
        addButton = findViewById(R.id.fab_main)
        addButton.setOnClickListener {
            val intent = newIntentAddItem(this)
            startActivity(intent)
        }

    }

    private fun setupRecyclerView() {
        val shopListRV = findViewById<RecyclerView>(R.id.recyclerView_main)
        shopListAdapter = ShopListAdapter()
        with(shopListRV) {
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
            val intent = newIntentEditItem(this, it.id)
            startActivity(intent)
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