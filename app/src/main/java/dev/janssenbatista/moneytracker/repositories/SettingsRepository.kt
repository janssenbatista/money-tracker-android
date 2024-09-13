package dev.janssenbatista.moneytracker.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStore: DataStore<Preferences>) {

    suspend fun setShowIntroduction(showIntroduction: Boolean) {
        dataStore.edit { preferences ->
            preferences[SHOW_INTRODUCTION] = showIntroduction
        }
    }

    fun getShowIntroduction(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[SHOW_INTRODUCTION] ?: true
    }

    suspend fun setSelectedCurrency(selectedCurrency: String) {
        dataStore.edit { preferences ->
            preferences[SELECTED_CURRENCY] = selectedCurrency
        }
    }

    fun getSelectedCurrency(): Flow<String> = dataStore.data.map { preferences ->
        preferences[SELECTED_CURRENCY] ?: ""
    }

    companion object {
        private val SHOW_INTRODUCTION = booleanPreferencesKey("show_introduction")
        private val SELECTED_CURRENCY = stringPreferencesKey("selected_currency")
    }
}