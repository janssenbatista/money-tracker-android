package dev.janssenbatista.moneytracker

import android.app.Application
import dev.janssenbatista.moneytracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoneyTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoneyTrackerApplication)
            modules(appModule)
        }
    }
}