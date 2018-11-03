package com.example.mostafahussien.practicekotlin2.UI

import android.support.v7.util.DiffUtil
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */

class RecipeDiffUtilCallback(
        private val oldRecipeList: MutableList<Recipe>?,
        private val newRecipeList: MutableList<Recipe>?
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldRecipeList?.size!!
    }

    override fun getNewListSize(): Int {
        return newRecipeList?.size!!
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRecipeList?.get(oldItemPosition)?.recipeId == newRecipeList?.get(newItemPosition)?.recipeId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRecipeList?.get(oldItemPosition) == newRecipeList?.get(newItemPosition)
    }
}
