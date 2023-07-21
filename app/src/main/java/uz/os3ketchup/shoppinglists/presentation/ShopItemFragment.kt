package uz.os3ketchup.shoppinglists.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import uz.os3ketchup.shoppinglists.R
import uz.os3ketchup.shoppinglists.domain.ShopItem.Companion.UNDEFINED_ID
import java.lang.RuntimeException

class ShopItemFragment : Fragment() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSave: Button
    private lateinit var shopItemViewModel: ShopItemViewModel

    private var shopItemMode: String = UNKNOWN_MODE
    private var shopItemId: Int = UNDEFINED_ID
    private var onEditingFinishedListener:OnEditingFinishedListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener){
            onEditingFinishedListener = context
        }else{
            throw RuntimeException("Activity is not implemented")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        addTextChangedListeners()
        launchRightMode()
        observeViewModel()
    }


    interface OnEditingFinishedListener{
        fun onEditingFinished()
    }


    private fun observeViewModel() {
        shopItemViewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) getString(R.string.error_input_name) else null
            tilName.error = message
        }

        shopItemViewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) getString(R.string.error_input_count) else null
            tilCount.error = message
        }
        shopItemViewModel.isFinish.observe(viewLifecycleOwner) {
            onEditingFinishedListener?.onEditingFinished()
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
        shopItemViewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        btnSave.setOnClickListener {
            shopItemViewModel.editShopItem(etName.text.toString(), etCount.text.toString())
        }

    }

    private fun parseParams() {
        if (!requireArguments().containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("param screen mode is absent")
        }
        val mode = requireArguments().getString(EXTRA_SCREEN_MODE)
        if (mode != ADD_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        shopItemMode = mode
        if (shopItemMode == EDIT_MODE) {
            if (!requireArguments().containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = requireArguments().getInt(SHOP_ITEM_ID, UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        btnSave = view.findViewById(R.id.btn_save)
    }



    companion object {
        private const val LOG = "EXTRA_MODE"
        private const val SHOP_ITEM_ID = "shop_item_id"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"
        private const val UNKNOWN_MODE = ""


        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, ADD_MODE)
                }
            }
        }


        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, EDIT_MODE)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }


}
