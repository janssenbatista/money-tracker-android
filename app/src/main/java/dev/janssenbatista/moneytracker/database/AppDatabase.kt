package dev.janssenbatista.moneytracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.janssenbatista.moneytracker.R
import dev.janssenbatista.moneytracker.daos.AccountDao
import dev.janssenbatista.moneytracker.database.converters.AmountConverter
import dev.janssenbatista.moneytracker.database.converters.DataConverter
import dev.janssenbatista.moneytracker.models.Account
import dev.janssenbatista.moneytracker.models.AccountType
import java.util.Date

@Database(entities = [Account::class], version = 1)
@TypeConverters(DataConverter::class, AmountConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    class AddAccountCallback(private val context: Context) :
        Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val accountName = context.getString(R.string.wallet)
            val createdAt = Date().time
            val updatedAt = Date().time
            db.execSQL(
                """INSERT INTO tb_accounts 
                VALUES 
                (1, '$accountName', 0.0, ${AccountType.WALLET.id}, 1, $createdAt, $updatedAt)"""
                    .trimMargin()
            )
        }
    }
}