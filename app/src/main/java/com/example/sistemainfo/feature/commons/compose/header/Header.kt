package com.example.sistemainfo.feature.commons.compose.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.sistemainfo.R
import com.example.sistemainfo.ui.theme.Grey65

@Composable
fun Header(
    onCloseClick: () -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = { openDialog.value = true },
            content = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            })
        CloseDialog(
            openDialog = openDialog.value,
            closeClickButton = onCloseClick,
            cancelClickButton = { openDialog.value = false }
        )
    }
}

@Composable
fun CloseDialog(
    openDialog: Boolean,
    closeClickButton: () -> Unit,
    cancelClickButton: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {},
            text = {
                Text(
                    text = stringResource(id = R.string.close_app_text),
                    color = Grey65
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = cancelClickButton) {
                        Text(text = stringResource(id = R.string.cancel_close_app))
                    }
                    TextButton(onClick = closeClickButton) {
                        Text(
                            text = stringResource(id = R.string.ok_close_app),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
        )
    }
}


