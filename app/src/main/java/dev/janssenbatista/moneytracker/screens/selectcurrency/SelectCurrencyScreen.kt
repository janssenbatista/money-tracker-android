package dev.janssenbatista.moneytracker.screens.selectcurrency

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.janssenbatista.moneytracker.R
import dev.janssenbatista.moneytracker.repositories.SettingsRepository
import dev.janssenbatista.moneytracker.screens.accountList.AccountListScreen
import dev.janssenbatista.moneytracker.screens.accountList.AccountListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

class SelectCurrencyScreen(private val showBackButton: Boolean) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val currencies = stringArrayResource(id = R.array.currencies).toList()
        val navigator = LocalNavigator.currentOrThrow
        val settingsRepository: SettingsRepository = koinInject()
        val coroutineScope = rememberCoroutineScope()
        val accountListViewModel: AccountListViewModel = koinViewModel()

        var selectedCurrency by remember {
            mutableStateOf("")
        }
        var searchText by remember {
            mutableStateOf("")
        }
        var filteredCurrencies by remember {
            mutableStateOf(currencies)
        }

        LaunchedEffect(Unit) {
            settingsRepository.getSelectedCurrency().collect {
                selectedCurrency = it
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.select_a_currency)) },
                    navigationIcon = {
                        if (showBackButton) {
                            IconButton(onClick = { navigator.pop() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.go_back)
                                )
                            }
                        }
                    },
                    actions = {
                        if (selectedCurrency.isNotBlank()) {
                            IconButton(onClick = {
                                navigator.push(AccountListScreen(accountListViewModel.accountListState.value))
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = if (showBackButton)
                                        stringResource(R.string.save) else stringResource(
                                        R.string.go_to_account_list_screen
                                    )
                                )
                            }
                        }
                    })
            }

        ) { padding ->
            Column(Modifier.padding(padding)) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { value ->
                        searchText = value
                        filteredCurrencies =
                            currencies.filter { it.contains(searchText, ignoreCase = true) }
                    },
                    trailingIcon = {
                        if (searchText.isBlank()) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(R.string.search_currency)
                            )
                        } else {
                            IconButton(onClick = {
                                filteredCurrencies = currencies
                                searchText = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = stringResource(R.string.clear_search)
                                )
                            }
                        }
                    },
                    placeholder = { Text(text = stringResource(R.string.search_currency)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                LazyColumn(Modifier.padding(horizontal = 16.dp)) {
                    itemsIndexed(filteredCurrencies) { index, item ->
                        Column(Modifier.clickable {
                            selectedCurrency = item
                            coroutineScope.launch {
                                settingsRepository.setSelectedCurrency(selectedCurrency)
                            }
                        }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = item,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp, horizontal = 8.dp),
                                    fontWeight = if (item.equals(
                                            selectedCurrency,
                                            true
                                        )
                                    ) FontWeight.Bold else FontWeight.Normal
                                )
                                if (item.equals(
                                        selectedCurrency,
                                        true
                                    )
                                ) {
                                    Icon(imageVector = Icons.Default.Check, contentDescription = "")
                                }
                            }
                            if (index < currencies.size - 1) {
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }

        }
    }
}