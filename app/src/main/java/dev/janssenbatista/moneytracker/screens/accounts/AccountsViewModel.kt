package dev.janssenbatista.moneytracker.screens.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.janssenbatista.moneytracker.models.Account
import dev.janssenbatista.moneytracker.repositories.AccountRepository
import dev.janssenbatista.moneytracker.repositories.SettingsRepository
import dev.janssenbatista.moneytracker.utils.CurrencyUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountsViewModel(
    private val accountRepository: AccountRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _accountListState = MutableStateFlow(AccountListState())
    val accountListState = _accountListState.asStateFlow()

    init {
        viewModelScope.launch {
            accountRepository.getAllAccounts().collect { accounts ->
                _accountListState.update {
                    it.copy(accounts = accounts)
                }
            }
        }
        viewModelScope.launch {
            settingsRepository.getSelectedCurrency().collect { currency ->
                _accountListState.update {
                    it.copy(symbol = CurrencyUtils.extractSymbolFromCurrency(currency))
                }
            }
        }
    }


}

data class AccountListState(
    val accounts: List<Account> = emptyList(),
    val symbol: String = ""
)