package com.qstest.app

import android.app.Application
import com.qstest.injection.appModule
import com.qstest.injection.productModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class QSApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //Initialize the Koin for Dependency Injection
        startKoin {
            androidContext(this@QSApp)
            modules(
                listOf(
                    appModule,
                    productModule
                )
            )
        }
    }
}