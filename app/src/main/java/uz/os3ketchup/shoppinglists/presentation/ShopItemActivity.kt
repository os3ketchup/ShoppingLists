package uz.os3ketchup.shoppinglists.presentation

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

class ShopItemActivity : AppCompatActivity() {
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSave: Button
    private lateinit var shopItemViewModel: ShopItemViewModel
    private var shopItemMode = UNKNOWN_MODE
    private var shopItemId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()
        addTextChangedListeners()
        launchRightMode()
        observeViewModel()

    }

    private fun observeViewModel() {
        shopItemViewModel.errorInputName.observe(this) {
            val message = if (it) getString(R.string.error_input_name) else null
            tilName.error = message
        }

        shopItemViewModel.errorInputCount.observe(this) {
            val message = if (it) getString(R.string.error_input_count) else null
            tilCount.error = message
        }
    }

    private fun launchRightMode() {
        when (shopItemMode) {
            EDIT_MODE -> launchEditMode()
            ADD_MODE -> launchAddMode()
        }
    }

    private fun addTextChangedListeners() {

        val watcherName = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopItemViewModel.resetInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        etName.addTextChangedListener(watcherName)


        val watcherCount = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopItemViewModel.resetInputCount()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        etName.addTextChangedListener(watcherCount)

    }

    private fun launchAddMode() {
        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val count = etCount.text.toString()
            shopItemViewModel.addShopItem(inputName = name, inputCount = count)
        }
    }

    private fun launchEditMode() {
        shopItemViewModel.getShopItem(id = shopItemId)
        shopItemViewModel.shopItem.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        btnSave.setOnClickListener {
            shopItemViewModel.editShopItem(etName.text.toString(), etCount.text.toString())
        }

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

    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        btnSave = findViewById(R.id.btn_save)
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
}