package com.example.mostafahussien.practicekotlin2.UI.recipes

import com.example.mostafahussien.practicekotlin2.UI.Base.BasePresenter
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
interface RecipePresenter <V : RecipesView> : BasePresenter<V> {
    fun getRecipes()
    fun getRecipe(recipeId : String)
    fun saveRecipe(recipe : Recipe?)
}