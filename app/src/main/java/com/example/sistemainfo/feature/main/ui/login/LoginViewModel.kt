package com.example.sistemainfo.feature.main.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemainfo.domain.model.User
import com.example.sistemainfo.domain.repository.UserRepository
import com.example.sistemainfo.feature.commons.Constants.Companion.EMPTY_STRING
import com.example.sistemainfo.feature.commons.Constants.Companion.ID_NUMBER_ONE
import com.example.sistemainfo.feature.commons.Constants.Companion.ID_NUMBER_ZERO
import com.example.sistemainfo.feature.commons.viewModel.ChannelEventSenderImpl
import com.example.sistemainfo.feature.commons.viewModel.EventSender
import com.example.sistemainfo.feature.main.HomeFlowViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel(), EventSender<LoginViewModel.ScreenEvent> by ChannelEventSenderImpl() {

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

    fun onActionEvent(action: LoginScreenAction) {
        when (action) {
            LoginScreenAction.OnCloseClicked -> closeApp()
            LoginScreenAction.OnSignUpButtonClicked -> verifyUserRegister()
        }
    }

    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        user = repository.getUserFromRoom(ID_NUMBER_ONE) ?: user
    }

    private fun verifyUserRegister() {
        if (user.cpf.isEmpty() || user.name.isEmpty()) navigateToUserRegistration() else navigateToHome()
    }

    private fun navigateToHome() {
        viewModelScope.sendEvent(ScreenEvent.NavigateTo(HomeFlowViewModel.Navigation.Home))
    }

    private fun navigateToUserRegistration() {
        viewModelScope.sendEvent(ScreenEvent.NavigateTo(HomeFlowViewModel.Navigation.UserRegistration))
    }

    private fun closeApp() {
        viewModelScope.sendEvent(ScreenEvent.Close)
    }

    sealed class ScreenEvent {
        object Close : ScreenEvent()
        data class NavigateTo(val navigation: HomeFlowViewModel.Navigation) : ScreenEvent()
    }

    sealed class LoginScreenAction {
        object OnCloseClicked : LoginScreenAction()
        object OnSignUpButtonClicked : LoginScreenAction()
    }
}