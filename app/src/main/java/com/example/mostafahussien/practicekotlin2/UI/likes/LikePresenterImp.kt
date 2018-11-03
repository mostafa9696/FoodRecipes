package com.example.mostafahussien.practicekotlin2.UI.likes

import com.example.mostafahussien.practicekotlin2.UI.Base.BasePresenterImp
import com.example.mostafahussien.practicekotlin2.data.Config
import com.example.mostafahussien.practicekotlin2.data.RecipeDataManager
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Mostafa Hussien on 26/10/2018.
 */
class LikePresenterImp<V : LikeView> @Inject constructor(
        private val dataManager: RecipeDataManager,
        private val compositeDisposable: CompositeDisposable
) : BasePresenterImp<V>(dataManager, compositeDisposable), LikePresenter<V> {

    override fun getRecipes() {
        compositeDisposable.addAll(
                dataManager.selectRecipes()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ recipes ->
                            if (!isViewAttatched())
                                return@subscribe
                            if (recipes != null) {
                                baseView?.updateLikeList(recipes)
                            }
                        }, { throwable ->
                            if (!isViewAttatched())
                                return@subscribe
                            baseView?.showMessage("Error ,try again ! ")

                        })
        )
    }

    override fun getRecipe(recipeId: String) {
        baseView?.showLoading()
        compositeDisposable.addAll(
                dataManager.getRecipe(Config.API_KEY, recipeId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ singleRecipe ->
                            if (!isViewAttatched())
                                return@subscribe
                            baseView?.hideLoading()
                            if (singleRecipe != null)
                                baseView?.showIngredients(singleRecipe.recipe?.ingredients)
                        }, { throwable ->
                            if (!isViewAttatched())
                                return@subscribe
                            baseView?.hideLoading()
                            baseView?.showMessage("Error ,try again ! ")

                        })
        )
    }

    override fun removeRecipe(recipe: Recipe) {
        Observable.defer { Observable.just(dataManager.deleteRecipe(recipe)) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ ->
                    if (!isViewAttatched())
                        return@subscribe
                    baseView?.showMessage("Recipe Removed")
                } , { throwable ->
                    if (!isViewAttatched())
                        return@subscribe
                    baseView?.hideLoading()
                    baseView?.showMessage("Error ,try again ! ")

                })
    }
}