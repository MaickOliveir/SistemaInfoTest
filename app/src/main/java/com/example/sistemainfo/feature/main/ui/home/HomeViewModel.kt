package com.example.sistemainfo.feature.main.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemainfo.domain.model.User
import com.example.sistemainfo.domain.repository.UserRepository
import com.example.sistemainfo.feature.commons.Constants
import com.example.sistemainfo.feature.commons.Constants.Companion.ID_NUMBER_ONE
import com.example.sistemainfo.feature.commons.Constants.Companion.ID_NUMBER_ZERO
import com.example.sistemainfo.feature.commons.viewModel.ChannelEventSenderImpl
import com.example.sistemainfo.feature.commons.viewModel.EventSender
import com.example.sistemainfo.feature.main.HomeFlowViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel(), EventSender<HomeViewModel.ScreenEvent> by ChannelEventSenderImpl() {

    var user by mutableStateOf(
        User(
            id = ID_NUMBER_ZERO,
            code = Constants.EMPTY_STRING,
            name = Constants.EMPTY_STRING,
            cpf = Constants.EMPTY_STRING,
            address = Constants.EMPTY_STRING,
            phone = Constants.EMPTY_STRING
        )
    )

    fun onActionEvent(action: HomeScreenAction) {
        when (action) {
            HomeScreenAction.OnCloseClicked -> closeApp()
            HomeScreenAction.OnRegisterButtonClicked -> navigateToUserRegistration()
        }
    }

    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        user = repository.getUserFromRoom(ID_NUMBER_ONE) ?: user
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

    sealed class HomeScreenAction {
        object OnCloseClicked : HomeScreenAction()
        object OnRegisterButtonClicked : HomeScreenAction()
    }
}