package com.example.mostafahussien.practicekotlin2.UI.search

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.mostafahussien.practicekotlin2.DI.ApplicationContext
import dagger.Module
import dagger.Provides

/**
 * Created by Mostafa Hussien on 03/11/2018.
 */

@Module
class SearchModule {
    @Provides
    fun provideSearchMvpPresenter(presenter: SearchPresenterImp<SearchView>): SearchPresenter<SearchView> {
        return presenter
    }

    @Provides
    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    @Provides
    fun provideSearchAdapter(): SearchAdapter {
        return SearchAdapter(ArrayList())
    }
}