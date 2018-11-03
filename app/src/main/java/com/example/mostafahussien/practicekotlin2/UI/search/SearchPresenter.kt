package com.example.mostafahussien.practicekotlin2.UI.search

import com.example.mostafahussien.practicekotlin2.UI.Base.BasePresenter
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe
import io.reactivex.subjects.PublishSubject

/**
 * Created by Mostafa Hussien on 03/11/2018.
 */
interface SearchPresenter <V : SearchView> : BasePresenter<V> {
    fun search(subject: PublishSubject<String>)
    fun getNextPage()
    fun saveRecipe(recipe: Recipe?)
}