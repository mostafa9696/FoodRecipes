package com.example.mostafahussien.practicekotlin2.data.network.model.recipes

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */

data class Recipes (
        @SerializedName("count")
        @Expose
        var count : Int?=null,

        @SerializedName("recipes")
        @Expose
        var recipesResult: MutableList<Recipe>? = null

)