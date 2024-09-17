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
import java.util.Locale

class AccountsViewModel(
    private val accountRepository: AccountRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _accountsState = MutableStateFlow(AccountsState())
    val accountListState = _accountsState.asStateFlow()

    init {
        _accountsState.update { accountsState ->
            accountsState.copy(
                getAllAccounts = {
                    viewModelScope.launch {
                        accountRepository.getAllAccounts().collect { accounts ->
                            _accountsState.update {
                                it.copy(accounts = accounts)
                            }
                        }
                    }
                }, setShowingIntroduction = { isShowingIntroduction ->
                    viewModelScope.launch {
                        settingsRepository.setShowingIntroduction(isShowingIntroduction)
                    }
                })
        }
        viewModelScope.launch {
            settingsRepository.getSelectedCurrency().collect { currency ->
                _accountsState.update {
                    val symbol = CurrencyUtils.extractSymbolFromCurrency(currency)
                    it.copy(locale = CurrencyUtils.getLocaleByCurrencySymbol(symbol))
                }
            }
        }
        viewModelScope.launch {
            settingsRepository.isShowingIntroduction().collect { isShowingIntroduction ->
                _accountsState.update {
                    it.copy(isShowingIntroduction = isShowingIntroduction)
                }
            }
        }
    }


}

data class AccountsState(
    val accounts: List<Account> = emptyList(),
    val locale: Locale? = Locale.getDefault(),
    val isShowingIntroduction: Boolean = true,
    val getAllAccounts: () -> Unit = {},
    val setShowingIntroduction: (Boolean) -> Unit = {}
)