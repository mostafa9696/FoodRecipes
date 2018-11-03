package com.example.mostafahussien.practicekotlin2.data

import com.example.mostafahussien.practicekotlin2.data.db.RecipeFavoriteDBHelper
import com.example.mostafahussien.practicekotlin2.data.db.RecipeFavoriteDBHelperImp
import com.example.mostafahussien.practicekotlin2.data.network.RecipeApiHelper
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipes
import com.example.mostafahussien.practicekotlin2.data.network.model.singleRecipe.SingleRecipe
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Mostafa Hussien on 20/10/2018.
 */

// class for implements RecipeDataManager which contains all webserice call and DB transactions
class RecipeDataMangerImp @Inject
 constructor(private val recipeApiHelper: RecipeApiHelper, private val recipeFavoriteDBHelper: RecipeFavoriteDBHelperImp) : RecipeDataManager {


    override fun insertRecipe(recipe: Recipe?): Long = recipeFavoriteDBHelper.insertRecipe(recipe)

    override fun selectRecipes(): Flowable<MutableList<Recipe>> = recipeFavoriteDBHelper.selectRecipes()

    override fun getRecipes(key: String, page: String, count: String): Observable<Recipes> = recipeApiHelper.getRecipes(key, page, count)

    override fun getRecipe(key: String, rId: String): Observable<SingleRecipe> = recipeApiHelper.getRecipe(key, rId)

    override fun searchRecipes(key: String, query: String, page: String): Observable<Recipes> = recipeApiHelper.searchRecipes(key, query, page)

    override fun selectRecipe(recipeId: String): MutableList<Recipe> = recipeFavoriteDBHelper.selectRecipe(recipeId)

    override fun deleteRecipe(recipe: Recipe?) = recipeFavoriteDBHelper.deleteRecipe(recipe)
}