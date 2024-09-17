package dev.janssenbatista.moneytracker.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.janssenbatista.moneytracker.R

@Composable
fun DeleteAccountDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    accountDescription: String
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        },
        title = { Text(text = stringResource(R.string.delete_account)) },
        text = {
            Text(
                text = stringResource(
                    R.string.are_you_sure_you_want_to_delete_account,
                    accountDescription
                )
            )
        })
}