package com.example.sistemainfo.feature.main.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.sistemainfo.R
import com.example.sistemainfo.feature.commons.Constants.Companion.EMPTY_STRING
import com.example.sistemainfo.feature.commons.Constants.Companion.USER_LOGIN
import com.example.sistemainfo.feature.commons.Constants.Companion.USER_PASS
import com.example.sistemainfo.feature.commons.compose.header.Header
import com.example.sistemainfo.feature.commons.compose.image.CustomImage
import com.example.sistemainfo.feature.commons.dimen.Size
import com.example.sistemainfo.feature.main.HomeFlowViewModel
import com.example.sistemainfo.feature.main.MainActivity
import com.example.sistemainfo.ui.theme.Background
import com.example.sistemainfo.ui.theme.Grey4C
import com.example.sistemainfo.ui.theme.Pink40

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    flowViewModel: HomeFlowViewModel
) {
    val activity = LocalContext.current as MainActivity
    LaunchedEffect(key1 = viewModel) { viewModel.getUser() }
    Screen(viewModel = viewModel, onActionEvent = viewModel::onActionEvent)
    EventConsumer(activity = activity, flowViewModel = flowViewModel, viewModel = viewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    flowViewModel: HomeFlowViewModel,
    viewModel: LoginViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                LoginViewModel.ScreenEvent.Close -> activity.finish()
                is LoginViewModel.ScreenEvent.NavigateTo -> flowViewModel.navigate(event.navigation)
            }
        }
    }
}

@Composable
private fun Screen(
    viewModel: LoginViewModel,
    onActionEvent: (LoginViewModel.LoginScreenAction) -> Unit,
) {
    var textUser by remember { mutableStateOf(EMPTY_STRING) }
    var textPass by remember { mutableStateOf(EMPTY_STRING) }
    var isError by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = Size.SizeSM,
                vertical = Size.SizeSM
            ).background(Background)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Header(onCloseClick = { viewModel.onActionEvent(LoginViewModel.LoginScreenAction.OnCloseClicked) })
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CustomImage()
        }
        DefaultTextField(
            value = textUser,
            onValueChange = { textUser = it },
            labelValue = stringResource(id = R.string.user_label_text_field)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.size_sm)))
        PasswordTextField(
            value = textPass,
            onValueChange = { textPass = it },
            labelValue = stringResource(id = R.string.password_label_text_field)
        )
        ErrorText(isError = isError)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.size_lg)))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Size.Size8XLG),
            contentAlignment = Alignment.Center
        ) {
            OutlinedButton(
                onClick = {
                    if (textUser == USER_LOGIN && textPass == USER_PASS) {
                        onActionEvent(LoginViewModel.LoginScreenAction.OnSignUpButtonClicked)
                    } else {
                        isError = true
                    }
                },
                border = BorderStroke(width = Size.Size1, color = Grey4C)
            ) {
                Text(
                    text = stringResource(id = R.string.next_button),
                    color = Pink40
                )
                Icon(
                    modifier = Modifier.padding(start = Size.SizeMD),
                    imageVector = Icons.Default.ArrowForward,
                    tint = Pink40,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun ErrorText(isError: Boolean) {
    if (isError) {
        Text(
            text = stringResource(id = R.string.user_error),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun DefaultTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelValue: String
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelValue) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = Grey4C,
            focusedIndicatorColor = Grey4C
        )
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelValue: String
) {
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }
    val isChecked = remember { mutableStateOf(false) }

    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = labelValue) },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),
                    contentDescription = null
                )
            },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = Grey4C,
                focusedIndicatorColor = Grey4C
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = {
                    isChecked.value = it
                    showPassword.value = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Pink40
                )
            )
            Text(text = stringResource(id = R.string.show_password_checkbox))
        }
    }
}

