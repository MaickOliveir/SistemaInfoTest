package com.example.sistemainfo.feature.main.ui.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.sistemainfo.R
import com.example.sistemainfo.domain.model.User
import com.example.sistemainfo.feature.commons.Constants.Companion.EMPTY_STRING
import com.example.sistemainfo.feature.commons.Constants.Companion.ID_NUMBER_ONE
import com.example.sistemainfo.feature.commons.compose.header.Header
import com.example.sistemainfo.feature.commons.compose.image.CustomImage
import com.example.sistemainfo.feature.commons.dimen.Size
import com.example.sistemainfo.feature.main.HomeFlowViewModel
import com.example.sistemainfo.feature.main.MainActivity
import com.example.sistemainfo.ui.theme.Green13
import com.example.sistemainfo.ui.theme.Grey4C
import com.example.sistemainfo.ui.theme.Grey65
import com.example.sistemainfo.ui.theme.Pink40

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    flowViewModel: HomeFlowViewModel
) {
    val activity = LocalContext.current as MainActivity
    LaunchedEffect(key1 = viewModel) {}
    Screen(viewModel = viewModel, onActionEvent = viewModel::onActionEvent)
    EventConsumer(activity = activity, flowViewModel = flowViewModel, viewModel = viewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    flowViewModel: HomeFlowViewModel,
    viewModel: RegisterViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                RegisterViewModel.ScreenEvent.Close -> activity.finish()
                is RegisterViewModel.ScreenEvent.NavigateTo -> flowViewModel.navigate(event.navigation)
            }
        }
    }
}

@Composable
private fun Screen(
    viewModel: RegisterViewModel,
    onActionEvent: (RegisterViewModel.RegisterScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = Size.SizeSM,
                vertical = Size.SizeSM
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header { viewModel.onActionEvent(RegisterViewModel.RegisterScreenAction.OnCloseClicked) }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CustomImage()
        }
        Row(modifier = Modifier.padding(bottom = Size.SizepXSM)) {
            Text(
                modifier = Modifier.padding(end = Size.SizeXSM),
                text = stringResource(id = R.string.register_data_text)
            )
            Icon(
                imageVector = Icons.Default.AccountBox,
                tint = Grey65,
                contentDescription = null
            )
        }
        UserRegister(
            viewModel = viewModel,
            onActionEvent = onActionEvent
        )
    }
}

@Composable
fun UserRegister(
    viewModel: RegisterViewModel,
    onActionEvent: (RegisterViewModel.RegisterScreenAction) -> Unit
) {

    val openDialog = remember { mutableStateOf(false) }
    var code by remember { mutableStateOf(EMPTY_STRING) }
    var name by remember { mutableStateOf(EMPTY_STRING) }
    var cpf by remember { mutableStateOf(EMPTY_STRING) }
    var address by remember { mutableStateOf(EMPTY_STRING) }
    var phone by remember { mutableStateOf(EMPTY_STRING) }

    var isError by rememberSaveable { mutableStateOf(false) }
    var isCpfError by rememberSaveable { mutableStateOf(false) }

    CustomOutlinedTextField(
        value = code,
        onValueChange = { code = it },
        placeHolderText = stringResource(id = R.string.card_label_code_label),
        keyboardType = KeyboardType.Text
    )
    CustomOutlinedTextFieldForName(
        value = name,
        onValueChange = {
            name = it
            isError = false
        },
        isError = isError,
        placeHolderText = stringResource(id = R.string.card_label_name_label),
        keyboardType = KeyboardType.Text,
    )
    CustomOutlinedTextFieldForCPF(
        value = cpf,
        onValueChange = {
            cpf = it
            isError = false
            isCpfError = false
        },
        isCpfError = isCpfError,
        isError = isError,
        placeHolderText = stringResource(id = R.string.card_label_cpf_label),
        keyboardType = KeyboardType.Number
    )
    CustomOutlinedTextField(
        value = address,
        onValueChange = { address = it },
        placeHolderText = stringResource(id = R.string.card_label_address_label),
        keyboardType = KeyboardType.Text
    )
    CustomOutlinedTextField(
        value = phone,
        onValueChange = { phone = it },
        placeHolderText = stringResource(id = R.string.card_label_phone_label),
        keyboardType = KeyboardType.Phone
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.size_lg)))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Size.Size8XLG),
        contentAlignment = Alignment.Center
    ) {
        OutlinedButton(
            onClick = {
                val user = User(
                    id = ID_NUMBER_ONE,
                    code = code,
                    name = name,
                    cpf = cpf,
                    address = address,
                    phone = phone
                )
                if (user.cpf.isEmpty() || user.name.isEmpty()) {
                    isError = true
                } else if (user.cpf.length != 11) {
                    isCpfError = true
                } else {
                    openDialog.value = true
                    viewModel.addUser(user)
                }
            },
            border = BorderStroke(width = Size.Size1, color = Grey4C)
        ) {
            Text(
                text = stringResource(id = R.string.save_button),
                color = Pink40
            )
            Icon(
                modifier = Modifier.padding(start = Size.SizeSM),
                imageVector = Icons.Default.ArrowForward,
                tint = Pink40,
                contentDescription = null
            )
        }
        AlertDialogSample(
            openDialog = openDialog.value,
            onClick = {
                openDialog.value = false
                onActionEvent(RegisterViewModel.RegisterScreenAction.OnSaveButtonClicked)
            },
            cpfValue = cpf,
            onDismiss = {
                openDialog.value = false
                onActionEvent(RegisterViewModel.RegisterScreenAction.OnSaveButtonClicked)
            }
        )
    }
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolderText: String,
    keyboardType: KeyboardType
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier.padding(bottom = Size.SizeSM),
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeHolderText) },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Grey4C
        )
    )
}

@Composable
fun CustomOutlinedTextFieldForCPF(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolderText: String,
    keyboardType: KeyboardType,
    isError: Boolean,
    isCpfError: Boolean
) {
    val focusManager = LocalFocusManager.current

    Column {
        if (isError) {
            Text(
                text = stringResource(id = R.string.required_field_text),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
            )
        }
        if (isCpfError) {
            Text(
                text = stringResource(id = R.string.required_field_text_cpf),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
            )
        }
        OutlinedTextField(
            modifier = Modifier.padding(bottom = Size.SizeSM),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeHolderText) },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Grey4C
            )
        )
    }
}

@Composable
fun CustomOutlinedTextFieldForName(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolderText: String,
    keyboardType: KeyboardType,
    isError: Boolean
) {
    val focusManager = LocalFocusManager.current

    Column {
        if (isError) {
            Text(
                text = stringResource(id = R.string.required_field_text),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
            )
        }
        OutlinedTextField(
            modifier = Modifier.padding(bottom = Size.SizeSM),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeHolderText) },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Grey4C
            )
        )
    }
}

@Composable
fun AlertDialogSample(
    openDialog: Boolean = false,
    onClick: () -> Unit,
    cpfValue: String,
    onDismiss: () -> Unit
) {
    if (openDialog)
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = onDismiss,
            title = {
                Row {
                    Icon(
                        modifier = Modifier.padding(end = Size.SizeSM),
                        imageVector = Icons.Default.CheckCircle,
                        tint = Green13,
                        contentDescription = null
                    )
                    Text(text = stringResource(id = R.string.dialog_text))
                }
            },
            text = {
                Text(
                    text = cpfValue.substring(0, 4),
                    color = Grey65
                )
            },
            confirmButton = {
                TextButton(onClick = onClick) {
                    Text(
                        text = stringResource(id = R.string.dialog_button_text),
                        color = Pink40
                    )
                }
            },
        )
}






