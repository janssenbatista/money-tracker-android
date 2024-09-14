package dev.janssenbatista.moneytracker.utils

import java.util.Locale

object CurrencyUtils {

    fun getLocaleByCurrencySymbol(symbol: String): Locale? {
        val currencyToLocale = mapOf(
            "$" to Locale.US,
            "€" to Locale.GERMANY,
            "¥" to Locale.JAPAN,
            "£" to Locale.UK,
            "A$" to Locale("en", "AU"),
            "C$" to Locale("en", "CA"),
            "CHF" to Locale("de", "CH"),
            "¥" to Locale.CHINA,
            "kr" to Locale("sv", "SE"),
            "NZ$" to Locale("en", "NZ"),
            "MX$" to Locale("es", "MX"),
            "S$" to Locale("en", "SG"),
            "HK$" to Locale("zh", "HK"),
            "kr" to Locale("no", "NO"),
            "₩" to Locale.KOREA,
            "₺" to Locale("tr", "TR"),
            "₹" to Locale("hi", "IN"),
            "R$" to Locale("pt", "BR"),
            "R" to Locale("af", "ZA"),
            "₽" to Locale("ru", "RU"),
            "Rp" to Locale("id", "ID"),
            "RM" to Locale("ms", "MY"),
            "฿" to Locale("th", "TH"),
            "₱" to Locale("en", "PH"),
            "Kč" to Locale("cs", "CZ"),
            "zł" to Locale("pl", "PL"),
            "₪" to Locale("he", "IL"),
            "CLP$" to Locale("es", "CL"),
            "₨" to Locale("ur", "PK"),
            "SAR" to Locale("ar", "SA"),
            "AED" to Locale("ar", "AE"),
            "Ft" to Locale("hu", "HU"),
            "AR$" to Locale("es", "AR"),
            "COP$" to Locale("es", "CO"),
            "E£" to Locale("ar", "EG"),
            "₦" to Locale("en", "NG"),
            "₫" to Locale("vi", "VN"),
            "৳" to Locale("bn", "BD"),
            "lei" to Locale("ro", "RO"),
            "S/" to Locale("es", "PE"),
            "KSh" to Locale("sw", "KE"),
            "Rs" to Locale("si", "LK"),
            "₵" to Locale("ak", "GH"),
            "QAR" to Locale("ar", "QA"),
            "KD" to Locale("ar", "KW"),
            "MAD" to Locale("ar", "MA"),
            "TND" to Locale("ar", "TN"),
            "JOD" to Locale("ar", "JO"),
            "OMR" to Locale("ar", "OM")
        )

        return currencyToLocale[symbol]
    }

    fun extractSymbolFromCurrency(currency: String) = currency.split(" ")
        .last()
        .replace(Regex("[()]"), "")
}