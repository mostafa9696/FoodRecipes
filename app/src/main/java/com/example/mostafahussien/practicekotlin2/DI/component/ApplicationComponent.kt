package com.example.mostafahussien.practicekotlin2.DI.component


import com.example.mostafahussien.practicekotlin2.DI.module.ActivityBindingModule
import com.example.mostafahussien.practicekotlin2.DI.module.ApplicationModule
import com.example.mostafahussien.practicekotlin2.DI.module.NetworkModule
import com.example.mostafahussien.practicekotlin2.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton



    @Singleton
    @Component(
            modules =
            [
            AndroidSupportInjectionModule::class,     // Use AndroidInjectionModule.class if you're not using support library
            NetworkModule::class,
            ApplicationModule::class,
            ActivityBindingModule::class
            ]
    )
    interface ApplicationComponent {

        @Component.Builder      //  @Component.Builder to bind some instance to Component
        interface Builder {
            @BindsInstance
            fun application(mainApplication: MainApplication): Builder

            fun build(): ApplicationComponent
        }

        fun inject(mainApplication: MainApplication)

    }
