package com.example.mostafahussien.practicekotlin2.UI.Home

import dagger.Module
import dagger.Provides

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */

@Module
class MainModule {

    @Provides
    fun provideViewPagerAdapter(mainActivity: MainActivity): ViewPagerAdapter {
        return ViewPagerAdapter(mainActivity.supportFragmentManager)
    }

    @Provides
    fun provideMainPresenter(presenter: MainPresenterImp<MainView>) : MainPresenter<MainView>{
        return presenter
    }

}