package dev.janssenbatista.moneytracker.screens.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.janssenbatista.moneytracker.R
import dev.janssenbatista.moneytracker.components.AccountComponent
import dev.janssenbatista.moneytracker.repositories.SettingsRepository
import dev.janssenbatista.moneytracker.screens.home.HomeScreen
import dev.janssenbatista.moneytracker.utils.CurrencyUtils
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.text.NumberFormat

class AccountsScreen(private val accountListState: AccountListState) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val locale = CurrencyUtils.getLocaleByCurrencySymbol(accountListState.symbol)
        val numberFormat = NumberFormat.getCurrencyInstance(locale!!)
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        val settingsRepository: SettingsRepository = koinInject()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.accounts)) },
                    actions = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    settingsRepository.setShowIntroduction(true)
                                }.invokeOnCompletion {
                                    navigator.popAll()
                                    navigator.replace(HomeScreen())
                                }
                            }) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = stringResource(
                                R.string.confirm
                            )
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(
                                    id = R.string.go_back
                                )
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {  }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add_new_account))
                }
            }

        ) { padding ->
            Column(Modifier.padding(padding)) {
                accountListState.accounts.forEach { account ->
                    AccountComponent(account = account, numberFormat = numberFormat)
                }
            }
        }
    }
}
