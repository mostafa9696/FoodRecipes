package com.example.mostafahussien.practicekotlin2.UI.recipes

import com.example.mostafahussien.practicekotlin2.data.RecipeDataManager
import com.example.mostafahussien.practicekotlin2.UI.Base.BasePresenterImp
import com.example.mostafahussien.practicekotlin2.data.Config
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
class RecipePresenterImp<V : RecipesView> @Inject constructor(
        private val dataManager: RecipeDataManager,
        private val compositeDisposable: CompositeDisposable
) : BasePresenterImp<V>(dataManager, compositeDisposable), RecipePresenter<V> {

    private var page: Int = 1
    override fun getRecipes() {
        compositeDisposable.addAll(
                dataManager.getRecipes(
                        Config.API_KEY, page.toString(), "10"
                ).map { recipes ->
                    if (recipes.recipesResult != null) {
                        for (recipe in recipes.recipesResult!!) {
                            recipe.liked = dataManager.selectRecipe(recipe.recipeId).size > 0
                        }
                    }
                    recipes
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ recipes ->
                            if (!isViewAttatched())
                                return@subscribe
                            baseView?.hideLoading()
                            if (recipes.recipesResult != null) {
                                baseView?.updateRecipesList(recipes.recipesResult!!)
                                ++page
                            }
                        }, { throwable ->
                            if (!isViewAttatched())
                                return@subscribe
                            baseView?.hideLoading()
                            baseView?.showMessage("Error ,try again ! ")
                            baseView?.showRecyclerError()
                        })
        )
    }

    override fun saveRecipe(recipe: Recipe?) {
        Observable.defer { Observable.just(dataManager.insertRecipe(recipe)) }       // Observable.defer likes Observable.create but defer don’t want it to that process to happen until subscription.
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ ->
                    if (!isViewAttatched())
                        return@subscribe
                    baseView?.showMessage("Recipe Liked.")
                }, { throwable ->
                    if (!isViewAttatched())
                        return@subscribe
                    baseView?.showMessage("Error ,try again ! ")
                    Timber.e(throwable.message)
                })
    }

    override fun getRecipe(recipeId: String) {
        baseView?.showLoading()
        compositeDisposable.addAll(
                dataManager.getRecipe(
                        Config.API_KEY, recipeId
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ singleRecipe ->
                            if (!isViewAttatched())
                                return@subscribe
                            baseView?.hideLoading()
                            if (singleRecipe.recipe != null) {
                                baseView?.showIngredients(singleRecipe.recipe?.ingredients)
                            }
                        }, { throwable ->
                            if (!isViewAttatched())
                                return@subscribe
                            baseView?.hideLoading()
                            baseView?.showMessage("Error ,try again ! ")

                        })
        )
    }
}