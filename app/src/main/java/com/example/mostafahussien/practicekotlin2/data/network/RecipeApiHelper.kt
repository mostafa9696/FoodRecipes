package com.example.mostafahussien.practicekotlin2.data.network

import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipes
import com.example.mostafahussien.practicekotlin2.data.network.model.singleRecipe.SingleRecipe
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
class RecipeApiHelper @Inject constructor(val apiService : RecipeService) : RecipeHelper {

    override fun getRecipes(key: String, page: String, count: String): Observable<Recipes> = apiService.getRecipes(key, page, count)

    override fun getRecipe(key: String, rId: String): Observable<SingleRecipe> = apiService.getRecipe(key, rId)

    override fun searchRecipes(key: String, query: String, page: String): Observable<Recipes> = apiService.searchRecipes(key, query, page)

}