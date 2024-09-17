package dev.janssenbatista.moneytracker.models

enum class AccountType(val accountType: String, val id: Int) {
    WALLET("Wallet", 1),
    BANK("Bank", 2);

    companion object {
        fun getDescriptionById(id: Int): String {
            return entries.first { it.id == id }.accountType
        }
    }
}