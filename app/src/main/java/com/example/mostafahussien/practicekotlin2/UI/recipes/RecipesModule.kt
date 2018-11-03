package com.example.mostafahussien.practicekotlin2.UI.recipes

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.mostafahussien.practicekotlin2.DI.ApplicationContext
import dagger.Module
import dagger.Provides

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */

@Module
class RecipesModule {

    @Provides
    fun provideRecipesMvpPresenter(presenter: RecipePresenterImp<RecipesView>): RecipePresenter<RecipesView> {
        return presenter
    }

    @Provides
    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    @Provides
    fun provideRecipesItemAnimator(): RecipesItemAnimator {
        return RecipesItemAnimator()
    }

    @Provides
    fun provideRecipesAdapter(): RecipeAdapter {
        return RecipeAdapter(ArrayList())
    }
}