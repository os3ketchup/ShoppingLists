package uz.os3ketchup.shoppinglists.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uz.os3ketchup.shoppinglists.R
import uz.os3ketchup.shoppinglists.databinding.ActivityMainBinding
import uz.os3ketchup.shoppinglists.presentation.ShopItemActivity.Companion.newIntentAddItem
import uz.os3ketchup.shoppinglists.presentation.ShopItemActivity.Companion.newIntentEditItem
import uz.os3ketchup.shoppinglists.presentation.ShopListAdapter.Companion.MAX_POOL
import uz.os3ketchup.shoppinglists.presentation.ShopListAdapter.Companion.SHOP_ITEM_DISABLED
import uz.os3ketchup.shoppinglists.presentation.ShopListAdapter.Companion.SHOP_ITEM_ENABLED
import java.lang.RuntimeException

class MainActivity : AppCompatActivity(),ShopItemFragment.OnEditingFinishedListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    lateinit var binding:ActivityMainBinding


    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()
        mainViewModel.shopList.observe(this) {
            Log.d(SOMETHING, "onCreate:s $it")
            shopListAdapter.submitList(it)
        }

        binding.fabMain.setOnClickListener {
            if (isOnePaneMode()){
                val intent = newIntentAddItem(this)
                startActivity(intent)
            }else{

                launchFragment(ShopItemFragment.newInstanceAddItem())
            }


        }

    }

    private fun setupRecyclerView() {

        shopListAdapter = ShopListAdapter()
        with(binding.recyclerViewMain) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(SHOP_ITEM_ENABLED, MAX_POOL)
            recycledViewPool.setMaxRecycledViews(SHOP_ITEM_DISABLED, MAX_POOL)
        }
        setUpLongClickListener()
        setupClickListener()
        setupOnItemSwiped(binding.recyclerViewMain)
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
            if (isOnePaneMode()) {
                val intent = newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }

        }
    }

    private fun isOnePaneMode(): Boolean {
        return binding.fragmentContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setUpLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            mainViewModel.editShopItem(it)
        }
    }

    companion object {
        const val SOMETHING = "LOG ViewModel"

    }

    override fun onEditingFinished() {
        supportFragmentManager.popBackStack()
    }


}