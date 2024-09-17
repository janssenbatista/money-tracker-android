package dev.janssenbatista.moneytracker.screens.account

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.janssenbatista.moneytracker.R
import dev.janssenbatista.moneytracker.components.DeleteAccountDialog
import dev.janssenbatista.moneytracker.models.AccountType
import org.koin.androidx.compose.koinViewModel

class AccountScreen(private val accountId: Int? = 1) :
    Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        var isDropDownExpanded by remember {
            mutableStateOf(false)
        }

        var isDeleteAccountDialogVisible by remember {
            mutableStateOf(false)
        }

        val accountViewModel: AccountViewModel = koinViewModel()
        val accountState by accountViewModel.accountState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        val descriptionFocus = remember {
            FocusRequester()
        }
        val amountFocus = remember {
            FocusRequester()
        }


        LaunchedEffect(Unit) {
            descriptionFocus.requestFocus()
            accountId?.let {
                accountState.setId(accountId)
                accountState.getAccountById(accountId)
            }
        }

        LaunchedEffect(accountState.isAccountCreated) {
            if (accountState.isAccountCreated) {
                navigator.pop()
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = accountId?.let {
                        stringResource(R.string.update_account)
                    } ?: stringResource(R.string.create_account))
                }, navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back)
                        )
                    }
                }, actions = {
                    accountId?.let {
                        IconButton(onClick = {
                            isDeleteAccountDialogVisible = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(
                                    R.string.delete_account_cd,
                                    accountState.description
                                )
                            )
                        }
                    }
                })
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                OutlinedTextField(
                    value = accountState.description,
                    onValueChange = { value ->
                        accountState.setDescription(value)
                    },
                    label = { Text(text = stringResource(R.string.description)) },
                    maxLines = 1,
                    supportingText = {
                        if (accountState.descriptionErrorMessage.isNotBlank()) {
                            descriptionFocus.requestFocus()
                            Text(text = accountState.descriptionErrorMessage)
                        }
                    },
                    isError = accountState.descriptionErrorMessage.isNotBlank(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        amountFocus.requestFocus()
                    }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(descriptionFocus)

                )
                OutlinedTextField(
                    value = accountState.amount,
                    onValueChange = { value ->
                        accountState.setAmount(value)
                    },
                    label = { Text(text = stringResource(R.string.amount)) },
                    supportingText = {
                        if (accountState.amountErrorMessage.isNotBlank()) {
                            amountFocus.requestFocus()
                            Text(text = accountState.amountErrorMessage)
                        }
                    },
                    isError = accountState.amountErrorMessage.isNotBlank(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(amountFocus)
                        .padding(bottom = 0.dp)
                )
                Box {
                    OutlinedTextField(
                        value = AccountType.getDescriptionById(accountState.accountType),
                        readOnly = true,
                        label = { Text(text = stringResource(R.string.account_type)) },
                        onValueChange = { },
                        trailingIcon = {
                            IconButton(onClick = { isDropDownExpanded = !isDropDownExpanded }) {
                                Icon(
                                    imageVector = if (isDropDownExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isDropDownExpanded = !isDropDownExpanded
                            }
                    )
                    DropdownMenu(
                        expanded = isDropDownExpanded,
                        onDismissRequest = {
                            isDropDownExpanded = !isDropDownExpanded
                        }) {
                        AccountType.entries.forEach { accountType ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = accountType.accountType)
                                },
                                onClick = {
                                    isDropDownExpanded = !isDropDownExpanded
                                    accountState.setAccountType(accountType)
                                })
                        }
                    }
                }
                Row(
                    Modifier.clickable {
                        accountState.setShowInTotalBalance(!accountState.showInTotalBalance)
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = accountState.showInTotalBalance, onCheckedChange = {
                        accountState.setShowInTotalBalance(it)
                    })
                    Text(text = stringResource(R.string.show_in_total_balance))
                }
                Button(
                    onClick = {
                        accountState.createAccount(
                            context.getString(R.string.description_must_contain_at_least_2_characters),
                            context.getString(R.string.invalid_amount)
                        )
                    }, modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.save))
                }
                if (isDeleteAccountDialogVisible) {
                    DeleteAccountDialog(
                        onDismissRequest = { isDeleteAccountDialogVisible = false },
                        onConfirm = {
                            accountState.deleteAccountById(accountId!!)
                            Toast.makeText(
                                context,
                                context.getString(R.string.account_deleted_successfully),
                                Toast.LENGTH_SHORT
                            ).show()
                            navigator.pop()
                        },
                        accountDescription = accountState.description
                    )
                }
            }
        }
    }

}
