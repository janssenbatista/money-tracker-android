package dev.janssenbatista.moneytracker.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.janssenbatista.moneytracker.models.Account
import dev.janssenbatista.moneytracker.models.AccountType
import dev.janssenbatista.moneytracker.repositories.AccountRepository
import dev.janssenbatista.moneytracker.utils.CurrencyUtils.isValidBigDecimal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class AccountViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    private val _accountState = MutableStateFlow(AccountState())
    val accountState = _accountState.asStateFlow()

    init {
        _accountState.update { accountState ->
            accountState.copy(
                setId = { id ->
                    _accountState.update {
                        it.copy(id = id)
                    }
                },
                setDescription = { description ->
                    _accountState.update {
                        it.copy(description = description, descriptionErrorMessage = "")
                    }
                },
                setAccountType = { accountType ->
                    _accountState.update {
                        it.copy(accountType = accountType.id)
                    }
                },
                setAmount = { amount ->
                    _accountState.update {
                        it.copy(amount = amount, amountErrorMessage = "")
                    }
                },
                setShowInTotalBalance = { showInTotalBalance ->
                    _accountState.update {
                        it.copy(showInTotalBalance = showInTotalBalance)
                    }
                },
                createAccount = { descriptionErrorMessage, amountErrorMessage ->
                    if (_accountState.value.description.trim().length < 2) {
                        _accountState.update {
                            it.copy(
                                descriptionErrorMessage = descriptionErrorMessage,
                                isAccountCreated = false
                            )
                        }
                        return@copy
                    }
                    if (!isValidBigDecimal(_accountState.value.amount.trim())) {
                        _accountState.update {
                            it.copy(
                                amountErrorMessage = amountErrorMessage,
                                isAccountCreated = false
                            )
                        }
                        return@copy
                    }
                    viewModelScope.launch {
                        val account = Account(
                            id = _accountState.value.id,
                            description = _accountState.value.description,
                            amount = _accountState.value.amount.toBigDecimal(),
                            accountType = _accountState.value.accountType,
                            showInTotalBalance = _accountState.value.showInTotalBalance,
                            createdAt = accountState.id?.let { accountState.createdAt } ?: Date(),
                            updatedAt = Date()
                        )
                        accountRepository.addAccount(account)
                    }.invokeOnCompletion {
                        _accountState.update {
                            it.copy(isAccountCreated = true)
                        }
                    }
                },
                getAccountById = { accountId ->
                    viewModelScope.launch {
                        accountRepository.getAccountById(accountId).collect { account ->
                            account?.let {
                                _accountState.update {
                                    it.copy(
                                        id = account.id,
                                        description = account.description,
                                        amount = account.amount.toPlainString(),
                                        accountType = account.accountType,
                                        showInTotalBalance = account.showInTotalBalance,
                                        createdAt = account.createdAt,
                                        updatedAt = account.updatedAt
                                    )
                                }
                            }
                        }
                    }
                },
                deleteAccountById = { accountId ->
                    viewModelScope.launch {
                        accountRepository.deleteAccountById(accountId)
                    }
                }
            )
        }
    }

}

data class AccountState(
    var id: Int? = null,
    var description: String = "",
    var descriptionErrorMessage: String = "",
    var amount: String = "",
    var amountErrorMessage: String = "",
    var accountType: Int = AccountType.BANK.id,
    var showInTotalBalance: Boolean = true,
    var createdAt: Date = Date(),
    var updatedAt: Date = Date(),
    var isAccountCreated: Boolean = false,
    val setId: (Int) -> Unit = {},
    val setDescription: (String) -> Unit = {},
    val setAmount: (String) -> Unit = {},
    val setAccountType: (AccountType) -> Unit = {},
    val setShowInTotalBalance: (Boolean) -> Unit = {},
    val createAccount: (String, String) -> Unit = { _, _ -> },
    val getAccountById: (Int) -> Unit = {},
    val deleteAccountById: (Int) -> Unit = {}
)
