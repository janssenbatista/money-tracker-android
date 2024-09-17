package dev.janssenbatista.moneytracker.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.janssenbatista.moneytracker.models.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Upsert
    fun addAccount(account: Account)

    @Query("SELECT * FROM tb_accounts")
    fun getAllAccounts(): Flow<List<Account>>

    @Query("SELECT * FROM tb_accounts WHERE id = :accountId")
    fun getAccountById(accountId: Int): Flow<Account?>

    @Query("DELETE FROM tb_accounts WHERE id = :accountId")
    fun deleteAccountById(accountId: Int)
}