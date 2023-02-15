package com.example.sistemainfo.feature.main.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemainfo.domain.model.User
import com.example.sistemainfo.domain.repository.UserRepository
import com.example.sistemainfo.feature.commons.viewModel.ChannelEventSenderImpl
import com.example.sistemainfo.feature.commons.viewModel.EventSender
import com.example.sistemainfo.feature.main.HomeFlowViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel(), EventSender<RegisterViewModel.ScreenEvent> by ChannelEventSenderImpl() {

    fun onActionEvent(action: RegisterScreenAction) {
        when (action) {
            RegisterScreenAction.OnCloseClicked -> closeApp()
            RegisterScreenAction.OnSaveButtonClicked -> navigateToHome()
        }
    }

    private fun navigateToHome() {
        viewModelScope.sendEvent(ScreenEvent.NavigateTo(HomeFlowViewModel.Navigation.Home))
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserToRoom(user)
        }
    }

    private fun closeApp() {
        viewModelScope.sendEvent(ScreenEvent.Close)
    }

    sealed class ScreenEvent {
        object Close : ScreenEvent()
        data class NavigateTo(val navigation: HomeFlowViewModel.Navigation) : ScreenEvent()
    }

    sealed class RegisterScreenAction {
        object OnCloseClicked : RegisterScreenAction()
        object OnSaveButtonClicked : RegisterScreenAction()
    }
}