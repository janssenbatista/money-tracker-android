package dev.janssenbatista.moneytracker.repositories

import dev.janssenbatista.moneytracker.database.AppDatabase
import dev.janssenbatista.moneytracker.models.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountRepository(appDatabase: AppDatabase) {

    private val accountDao = appDatabase.accountDao()

    fun getAllAccounts() = accountDao.getAllAccounts()

    fun getAccountById(accountId: Int) = accountDao.getAccountById(accountId)

    fun addAccount(account: Account) {
        CoroutineScope(Dispatchers.IO).launch {
            accountDao.addAccount(account)
        }
    }

    fun deleteAccountById(accountId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            accountDao.deleteAccountById(accountId)
        }
    }
}