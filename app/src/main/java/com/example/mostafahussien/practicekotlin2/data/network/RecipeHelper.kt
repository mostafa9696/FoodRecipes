package com.example.mostafahussien.practicekotlin2.data.network

import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipes
import com.example.mostafahussien.practicekotlin2.data.network.model.singleRecipe.SingleRecipe
import io.reactivex.Observable

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
interface RecipeHelper {

    fun getRecipes(key: String, page: String, count: String): Observable<Recipes>

    fun getRecipe(key: String, rId: String): Observable<SingleRecipe>

    fun searchRecipes(key: String, query: String, page: String): Observable<Recipes>


}