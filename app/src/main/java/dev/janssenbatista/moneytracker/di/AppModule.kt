package dev.janssenbatista.moneytracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import dev.janssenbatista.moneytracker.database.AppDatabase
import dev.janssenbatista.moneytracker.repositories.AccountRepository
import dev.janssenbatista.moneytracker.repositories.SettingsRepository
import dev.janssenbatista.moneytracker.screens.accounts.AccountsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val appModule = module {
    // DataStore
    single { provideDataStore(get()) }
    // Setting Repository
    single<SettingsRepository> { SettingsRepository(get()) }
    // AppDatabase
    single<AppDatabase> {
        Room.databaseBuilder(
            get<Context>().applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).addCallback(
            AppDatabase.AddAccountCallback(
                get<Context>().applicationContext,
            )
        ).build()
    }
    // Account Repository
    single<AccountRepository> { AccountRepository(get()) }
    // Accounts View Model
    viewModel<AccountsViewModel> {
        AccountsViewModel(
            get<AccountRepository>(),
            get<SettingsRepository>()
        )
    }
}

private fun provideDataStore(context: Context): DataStore<Preferences> = context.dataStore