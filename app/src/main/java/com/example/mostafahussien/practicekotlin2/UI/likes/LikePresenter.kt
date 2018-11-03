package com.example.mostafahussien.practicekotlin2.UI.likes

import com.example.mostafahussien.practicekotlin2.UI.Base.BasePresenter

import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe

/**
 * Created by Mostafa Hussien on 26/10/2018.
 */
interface LikePresenter<V : LikeView> : BasePresenter<V> {

    fun getRecipes()
    fun getRecipe(recipeId: String)
    fun removeRecipe(recipe: Recipe)
}