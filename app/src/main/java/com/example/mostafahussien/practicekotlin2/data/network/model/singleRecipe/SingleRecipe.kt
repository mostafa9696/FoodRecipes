package com.example.mostafahussien.practicekotlin2.data.network.model.singleRecipe

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */


class SingleRecipe {

    @SerializedName("recipe")
    @Expose
    var recipe: Recipe? = null

}