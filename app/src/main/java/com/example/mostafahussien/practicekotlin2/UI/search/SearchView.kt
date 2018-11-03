package com.example.mostafahussien.practicekotlin2.UI.search

import com.example.mostafahussien.practicekotlin2.UI.Base.BaseView
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe

/**
 * Created by Mostafa Hussien on 03/11/2018.
 */
interface SearchView : BaseView {
    fun refreshSearchList(recipes: MutableList<Recipe>)
    fun updateRecipeList(recipes: MutableList<Recipe>)
    fun showErrorInRecyclerView()
}