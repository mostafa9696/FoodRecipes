package com.example.mostafahussien.practicekotlin2.UI.likes

import com.example.mostafahussien.practicekotlin2.UI.Base.BaseView
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe

/**
 * Created by Mostafa Hussien on 26/10/2018.
 */
interface LikeView : BaseView {
    fun updateLikeList(recipes: MutableList<Recipe>?)
    fun showIngredients(ingredients: List<String>?)
}