package dev.janssenbatista.moneytracker.repositories

import dev.janssenbatista.moneytracker.database.AppDatabase

class AccountRepository(private val appDatabase: AppDatabase) {

    fun getAllAccounts() = appDatabase.accountDao().getAllAccounts()

    fun getAccountById(accountId: Int) = appDatabase.accountDao().getAccountById(accountId)
}