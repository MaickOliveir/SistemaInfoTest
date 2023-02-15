package com.example.sistemainfo.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.sistemainfo.feature.commons.ViewModelFactory
import com.example.sistemainfo.feature.commons.composeViewModel
import javax.inject.Inject

abstract class BaseComposeActivity<VM : ViewModel> : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var _flowViewModel: VM
    protected val flowViewModel get() = _flowViewModel

    protected val viewModelStoreOwner: ViewModelStoreOwner get() = this

    abstract fun viewModelClass(): Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createFlowViewModel()
    }

    /**
     * Try to get already created ViewModel, otherwise, create a new instance using factory.
     */
    private fun createFlowViewModel() = try {
        _flowViewModel = ViewModelProvider(this).get(viewModelClass())
    } catch (ex: Exception) {
        _flowViewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass())
    }

    /**
     * Create viewModel with compose lifecycle
     */
    @Composable
    protected inline fun <reified T : ViewModel> composeViewModel() =
        viewModelFactory.composeViewModel<T>()

    /**
     * Create viewModel with activity lifecycle (used to share data between compose screens)
     */
    @Composable
    protected inline fun <reified T : ViewModel> sharedComposeViewModel() =
        viewModelFactory.composeViewModel<T>(viewModelStoreOwner = viewModelStoreOwner)
}