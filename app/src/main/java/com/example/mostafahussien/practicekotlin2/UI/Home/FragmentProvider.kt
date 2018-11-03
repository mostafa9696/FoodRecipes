package com.example.mostafahussien.practicekotlin2.UI.Home

import com.example.mostafahussien.practicekotlin2.UI.likes.LikesFragment
import com.example.mostafahussien.practicekotlin2.UI.likes.LikesModule
import com.example.mostafahussien.practicekotlin2.UI.recipes.RecipesFragment
import com.example.mostafahussien.practicekotlin2.UI.recipes.RecipesModule
import com.example.mostafahussien.practicekotlin2.UI.search.SearchFragment
import com.example.mostafahussien.practicekotlin2.UI.search.SearchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */

@Module
abstract class FragmentProvider {
    @ContributesAndroidInjector(modules = [(RecipesModule::class)])
    internal abstract fun provideRecipesFragmentFactory(): RecipesFragment

    @ContributesAndroidInjector(modules = [(LikesModule::class)])
    internal abstract fun provideLikesFragmentFactory(): LikesFragment

    @ContributesAndroidInjector(modules = [(SearchModule::class)])
    internal abstract fun provideSearchFragmentFactory(): SearchFragment
}