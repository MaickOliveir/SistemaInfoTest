package com.example.sistemainfo.inject

import android.content.Context
import com.example.sistemainfo.feature.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }
}