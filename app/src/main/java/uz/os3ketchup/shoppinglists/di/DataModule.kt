package uz.os3ketchup.shoppinglists.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import uz.os3ketchup.shoppinglists.data.AppDatabase
import uz.os3ketchup.shoppinglists.data.ShopListDao
import uz.os3ketchup.shoppinglists.data.ShopListRepositoryImpl
import uz.os3ketchup.shoppinglists.domain.ShopListRepository

@Module

interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepository(impl:ShopListRepositoryImpl):ShopListRepository

    companion object{
        @Provides
        @ApplicationScope
        fun bindShopListDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}