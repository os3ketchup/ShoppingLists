package uz.os3ketchup.shoppinglists.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import uz.os3ketchup.shoppinglists.R
import uz.os3ketchup.shoppinglists.domain.ShopItem.Companion.UNDEFINED_ID
import java.lang.RuntimeException

class ShopItemActivity : AppCompatActivity(),ShopItemFragment.OnEditingFinishedListener {

    private var shopItemMode = UNKNOWN_MODE
    private var shopItemId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()

        launchRightMode()


    }



    @SuppressLint("CommitTransaction")
    private fun launchRightMode() {

       val fragment =  when (shopItemMode) {
           ADD_MODE -> ShopItemFragment.newInstanceAddItem()
           EDIT_MODE -> ShopItemFragment.newInstanceEditItem(shopItemId)
           else-> throw  RuntimeException("unknown screen $shopItemMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != ADD_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        shopItemMode = mode
        if (shopItemMode == EDIT_MODE) {
            if (!intent.hasExtra(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(SHOP_ITEM_ID, UNDEFINED_ID)
        }
    }

    companion object {
        private const val LOG = "EXTRA_MODE"
        private const val SHOP_ITEM_ID = "shop_item_id"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"
        private const val UNKNOWN_MODE = ""


        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, ADD_MODE)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, EDIT_MODE)
            intent.putExtra(SHOP_ITEM_ID, shopItemId)
            return intent
        }

    }

    override fun onEditingFinished() {
        finish()
    }
}