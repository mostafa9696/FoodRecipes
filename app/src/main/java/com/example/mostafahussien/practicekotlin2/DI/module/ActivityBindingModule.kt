package com.example.mostafahussien.practicekotlin2.DI.module

import com.example.mostafahussien.practicekotlin2.UI.Home.FragmentProvider
import com.example.mostafahussien.practicekotlin2.UI.Home.MainActivity
import com.example.mostafahussien.practicekotlin2.UI.Home.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Mostafa Hussien on 20/10/2018.
 */
@Module
abstract class ActivityBindingModule  {     // main module that contain sub component

    // @ContributesAndroidInjector --> easily attach activities/fragments to dagger graph
    @ContributesAndroidInjector(modules = [(MainModule::class), (FragmentProvider::class)])
    abstract fun bindHomeActivity(): MainActivity
}


//     @ContributesAndroidInjector(modules = SpecificActivity.class)        used for provide dependency for specific activity (scope) only

//     @ContributesAndroidInjector      used to any activity without limitation
