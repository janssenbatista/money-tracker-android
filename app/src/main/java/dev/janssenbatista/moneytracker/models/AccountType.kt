package dev.janssenbatista.moneytracker.models

enum class AccountType(val accountType: String, val id: Int) {
    WALLET("Wallet", 1),
    BANK("Bank", 2)
}