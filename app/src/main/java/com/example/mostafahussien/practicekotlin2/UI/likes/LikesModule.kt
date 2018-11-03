package com.example.mostafahussien.practicekotlin2.UI.likes

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.mostafahussien.practicekotlin2.DI.ApplicationContext
import dagger.Module
import dagger.Provides

/**
 * Created by Mostafa Hussien on 26/10/2018.
 */

@Module
class LikesModule {


    @Provides
    fun provideLikesMvpPresenter(presenter: LikePresenterImp<LikeView>): LikePresenter<LikeView> {
        return presenter
    }

    @Provides
    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    @Provides
    fun provideLikesAdapter(): LikeAdapter {
        return LikeAdapter(ArrayList())
    }
}