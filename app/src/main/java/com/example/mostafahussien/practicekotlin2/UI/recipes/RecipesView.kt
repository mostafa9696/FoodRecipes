package com.example.mostafahussien.practicekotlin2.UI.recipes

import com.example.mostafahussien.practicekotlin2.UI.Base.BaseView
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
interface RecipesView  : BaseView {
    fun updateRecipesList(recipes  : MutableList<Recipe>)
    fun showIngredients(ingredients: List<String>?)
    fun showRecyclerError()

}