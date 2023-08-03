package uz.os3ketchup.shoppinglists.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import uz.os3ketchup.shoppinglists.R

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, isError: Boolean) {
    val message =
        if (isError) textInputLayout.resources.getString(R.string.error_input_name) else null
    textInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, isError: Boolean) {
    val message =
        if (isError) textInputLayout.resources.getString(R.string.error_input_count) else null
    textInputLayout.error = message
}