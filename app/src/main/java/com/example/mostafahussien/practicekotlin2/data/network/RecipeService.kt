package com.example.mostafahussien.practicekotlin2.data.network

import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipes
import com.example.mostafahussien.practicekotlin2.data.network.model.singleRecipe.SingleRecipe
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */

interface RecipeService {

    @FormUrlEncoded
    @POST("search")
    fun getRecipes( @Field("key") key : String, @Field("page") page: String,@Field("count") count: String) : Observable<Recipes>


    @FormUrlEncoded
    @POST("get")
    fun getRecipe(@Field("key") key: String, @Field("rId") rId: String): Observable<SingleRecipe>

    @FormUrlEncoded
    @POST("search")
    fun searchRecipes(@Field("key") key: String, @Field("q") query: String, @Field("page") page: String): Observable<Recipes>

}