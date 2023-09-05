package uz.os3ketchup.shoppinglists.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import uz.os3ketchup.shoppinglists.presentation.MainActivity
import uz.os3ketchup.shoppinglists.presentation.ShopItemFragment

@ApplicationScope
@Component(modules = [DataModule::class,ViewModelModule::class])
interface ApplicationComponent {


    fun inject(activity: MainActivity)
    fun inject(shopItemFragment: ShopItemFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}