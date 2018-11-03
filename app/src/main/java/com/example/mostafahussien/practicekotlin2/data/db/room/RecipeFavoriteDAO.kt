package com.example.mostafahussien.practicekotlin2.data.db.room

import android.arch.persistence.room.*
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe
import io.reactivex.Flowable

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */

@Dao
interface RecipeFavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe : Recipe?) : Long

    @Query("select * from recipes")
    fun selectRecipes(): Flowable<MutableList<Recipe>>

    @Query("SELECT * FROM recipes WHERE recipe_id LIKE :recipeId")
    fun selectRecipe(recipeId: String): MutableList<Recipe>

    @Delete
    fun deleteRecipe(recipe: Recipe?)
}