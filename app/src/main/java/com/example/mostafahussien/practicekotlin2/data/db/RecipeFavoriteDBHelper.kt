package com.example.mostafahussien.practicekotlin2.data.db

import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe
import io.reactivex.Flowable

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
interface RecipeFavoriteDBHelper {

    fun insertRecipe(recipe : Recipe?) : Long

    fun selectRecipes() : Flowable<MutableList<Recipe>>         // Flowable use for backpressure aware which mean relatively many items over the time and there is no risk of overflooding consumer
                                                                // Flowable are observable queries. They allow you to get automatic updates whenever the data changes to make sure your UI reflects the latest values from your database

    fun selectRecipe(recipeId: String): MutableList<Recipe>

    fun deleteRecipe(recipe: Recipe?)
}