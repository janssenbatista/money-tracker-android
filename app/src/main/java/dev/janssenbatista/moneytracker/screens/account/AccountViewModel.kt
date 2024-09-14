package dev.janssenbatista.moneytracker.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.janssenbatista.moneytracker.models.Account
import dev.janssenbatista.moneytracker.repositories.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    private val _accountState = MutableStateFlow(AccountState())
    val accountState = _accountState.asStateFlow()

    fun getAccountById(accountId: Int) {
        viewModelScope.launch {
            accountRepository.getAccountById(accountId).collect { account ->
                _accountState.update {
                    it.copy(account = account)
                }
            }
        }
    }
}

data class AccountState(
    val account: Account? = null,
)