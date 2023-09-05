package uz.os3ketchup.shoppinglists

import android.app.Application
import dagger.internal.DaggerGenerated
import uz.os3ketchup.shoppinglists.di.ApplicationComponent
import uz.os3ketchup.shoppinglists.di.DaggerApplicationComponent

class ShopListApp: Application() {

        val component by lazy {
            DaggerApplicationComponent.factory().create(this)
        }
}