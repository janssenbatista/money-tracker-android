package dev.janssenbatista.moneytracker.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStore: DataStore<Preferences>) {

    suspend fun setShowingIntroduction(isShowingIntroduction: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_SHOWING_INTRODUCTION] = isShowingIntroduction
        }
    }

    fun isShowingIntroduction(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_SHOWING_INTRODUCTION] ?: true
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
        private val IS_SHOWING_INTRODUCTION = booleanPreferencesKey("is_showing_introduction")
        private val SELECTED_CURRENCY = stringPreferencesKey("selected_currency")
    }
}