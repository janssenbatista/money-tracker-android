package dev.janssenbatista.moneytracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val appModule = module {
    single { provideDataStore(get()) }
}

private fun provideDataStore(context: Context): DataStore<Preferences> = context.dataStore