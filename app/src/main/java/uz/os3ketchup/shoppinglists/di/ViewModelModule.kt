package uz.os3ketchup.shoppinglists.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uz.os3ketchup.shoppinglists.presentation.MainViewModel
import uz.os3ketchup.shoppinglists.presentation.ShopItemViewModel


@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel:MainViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel(viewModel: ShopItemViewModel):ViewModel
}