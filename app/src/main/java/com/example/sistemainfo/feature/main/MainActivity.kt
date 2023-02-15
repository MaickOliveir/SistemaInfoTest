package com.example.sistemainfo.feature.main

import android.os.Bundle
import android.view.KeyEvent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.sistemainfo.SistemaInfoApp
import com.example.sistemainfo.feature.commons.compose.navigation.composeNavigate
import com.example.sistemainfo.feature.commons.compose.navigation.setNavigationContent
import com.example.sistemainfo.feature.main.ui.home.HomeScreen
import com.example.sistemainfo.feature.main.ui.login.LoginScreen
import com.example.sistemainfo.feature.main.ui.register.RegisterScreen

open class MainActivity : BaseComposeActivity<HomeFlowViewModel>() {

    override fun viewModelClass() = HomeFlowViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as SistemaInfoApp).appComponent.inject(this)

        setNavigationContent(
            startDestination = HomeFlowViewModel.Navigation.Login.route,
            navGraphBuilder = this::navGraphBuilder,
            navEventFlow = flowViewModel.eventsFlow,
            navEvent = this::navEvent
        )
    }

    private fun navGraphBuilder(builder: NavGraphBuilder) = builder.apply {

        composable(HomeFlowViewModel.Navigation.Login.route) {
            LoginScreen(composeViewModel(), flowViewModel)
        }
        composable(HomeFlowViewModel.Navigation.UserRegistration.route) {
            RegisterScreen(composeViewModel(), flowViewModel)
        }
        composable(HomeFlowViewModel.Navigation.Home.route) {
            HomeScreen(composeViewModel(), flowViewModel)
        }
    }

    private fun navEvent(
        navController: NavHostController,
        navScreen: HomeFlowViewModel.Navigation
    ) {
        navController.composeNavigate(route = navScreen.route, popStack = navScreen.popStack)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}