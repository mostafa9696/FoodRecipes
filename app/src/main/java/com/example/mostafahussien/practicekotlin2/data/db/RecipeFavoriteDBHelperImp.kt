package com.example.mostafahussien.practicekotlin2.data.db

import com.example.mostafahussien.practicekotlin2.data.db.room.AppDatabase
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
class RecipeFavoriteDBHelperImp @Inject constructor(
        private val appDatabase: AppDatabase
): RecipeFavoriteDBHelper {

    override fun insertRecipe(recipe: Recipe?): Long = appDatabase.recipeDao().insertRecipe(recipe)

    override fun selectRecipes(): Flowable<MutableList<Recipe>> = appDatabase.recipeDao().selectRecipes()

    override fun selectRecipe(recipeId: String): MutableList<Recipe> = appDatabase.recipeDao().selectRecipe(recipeId)

    override fun deleteRecipe(recipe: Recipe?) = appDatabase.recipeDao().deleteRecipe(recipe)
}