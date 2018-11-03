package com.example.mostafahussien.practicekotlin2.data.db.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeFavoriteDAO
}