package com.example.sistemainfo.feature.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemainfo.domain.model.User
import com.example.sistemainfo.feature.commons.Constants.Companion.EMPTY_STRING
import com.example.sistemainfo.feature.commons.Constants.Companion.ID_NUMBER_ZERO
import com.example.sistemainfo.feature.commons.viewModel.ChannelEventSenderImpl
import com.example.sistemainfo.feature.commons.viewModel.EventSender
import javax.inject.Inject

class HomeFlowViewModel @Inject constructor() : ViewModel(),
    EventSender<HomeFlowViewModel.Navigation> by ChannelEventSenderImpl() {

    var user by mutableStateOf(
        User(
            id = ID_NUMBER_ZERO,
            code = EMPTY_STRING,
            name = EMPTY_STRING,
            cpf = EMPTY_STRING,
            address = EMPTY_STRING,
            phone = EMPTY_STRING
        )
    )

    fun navigate(navigation: Navigation) {
        viewModelScope.sendEvent(navigation)
    }

    sealed class Navigation(
        val route: String = EMPTY_STRING,
        val popStack: Boolean = false
    ) {
        object Login : Navigation(route = "login")
        object Home : Navigation(route = "home")
        object UserRegistration : Navigation(route = "user_registration")
    }
}