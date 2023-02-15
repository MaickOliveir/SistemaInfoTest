package com.example.sistemainfo.feature.main.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.sistemainfo.R
import com.example.sistemainfo.domain.model.User
import com.example.sistemainfo.feature.commons.compose.header.Header
import com.example.sistemainfo.feature.commons.compose.image.CustomImage
import com.example.sistemainfo.feature.commons.dimen.Size
import com.example.sistemainfo.feature.main.HomeFlowViewModel
import com.example.sistemainfo.feature.main.MainActivity
import com.example.sistemainfo.ui.theme.Background
import com.example.sistemainfo.ui.theme.Grey65

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    flowViewModel: HomeFlowViewModel,
) {
    val activity = LocalContext.current as MainActivity
    LaunchedEffect(key1 = viewModel) { viewModel.getUser() }
    HomeScreen(viewModel = viewModel, user = viewModel.user)
    EventConsumer(activity = activity, flowViewModel = flowViewModel, viewModel = viewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    flowViewModel: HomeFlowViewModel,
    viewModel: HomeViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                HomeViewModel.ScreenEvent.Close -> activity.finish()
                is HomeViewModel.ScreenEvent.NavigateTo -> flowViewModel.navigate(event.navigation)
            }
        }
    }
}

@Composable
private fun HomeScreen(
    viewModel: HomeViewModel,
    user: User
) =
    Column(
        modifier = Modifier
            .padding(
                horizontal = Size.SizeSM,
                vertical = Size.SizeSM
            )
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(onCloseClick = { viewModel.onActionEvent(HomeViewModel.HomeScreenAction.OnCloseClicked) })
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CustomImage()
        }
        Row {
            Text(
                modifier = Modifier.padding(end = Size.SizeXSM),
                text = stringResource(id = R.string.user_information_title)
            )
            Icon(
                imageVector = Icons.Default.AccountBox,
                tint = Grey65,
                contentDescription = null
            )
        }
        ShowUserInfoScreen(user = user)
    }

@Composable
fun ShowUserInfoScreen(
    user: User
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.padding(Size.SizeSM),
            backgroundColor = Color.White,
            border = BorderStroke(width = Size.Size1, color = Grey65),
            elevation = Size.SizeXSM
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Size.SizepXSM),
                horizontalAlignment = Alignment.Start,
            ) {
                UserInformation(
                    text = stringResource(id = R.string.card_label_code),
                    textValue = user.code,
                    icon = Icons.Default.Settings
                )
                Divider(color = Grey65, thickness = Size.Size1)
                UserInformation(
                    text = stringResource(id = R.string.card_label_name),
                    textValue = user.name,
                    icon = Icons.Default.Face
                )
                Divider(color = Grey65, thickness = Size.Size1)
                UserInformation(
                    text = stringResource(id = R.string.card_label_cpf),
                    textValue = user.cpf,
                    icon = Icons.Default.AccountBox
                )
                Divider(color = Grey65, thickness = Size.Size1)
                UserInformation(
                    text = stringResource(id = R.string.card_label_address),
                    textValue = user.address,
                    icon = Icons.Default.Home
                )
                Divider(color = Grey65, thickness = Size.Size1)
                UserInformation(
                    text = stringResource(id = R.string.card_label_phone),
                    textValue = user.phone,
                    icon = Icons.Default.Call
                )
            }
        }
    }
}

@Composable
fun UserInformation(
    text: String,
    textValue: String,
    icon: ImageVector
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.padding(top = Size.SizepXSM, bottom = Size.SizepXSM),
            text = text,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            modifier = Modifier.padding(top = Size.SizepXSM, bottom = Size.SizepXSM),
            text = textValue,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.align(Alignment.CenterVertically),
            imageVector = icon,
            tint = Grey65,
            contentDescription = null
        )
    }
}

