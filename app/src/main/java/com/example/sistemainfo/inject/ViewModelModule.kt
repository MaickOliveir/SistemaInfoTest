package com.example.sistemainfo.inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sistemainfo.feature.commons.ViewModelFactory
import com.example.sistemainfo.feature.commons.ViewModelKey
import com.example.sistemainfo.feature.main.HomeFlowViewModel
import com.example.sistemainfo.feature.main.ui.home.HomeViewModel
import com.example.sistemainfo.feature.main.ui.login.LoginViewModel
import com.example.sistemainfo.feature.main.ui.register.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeFlowViewModel::class)
    internal abstract fun bindHomeFlowViewModel(viewModel: HomeFlowViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    internal abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel
}