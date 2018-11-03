package com.example.mostafahussien.practicekotlin2.UI.search

import com.example.mostafahussien.practicekotlin2.UI.Base.BasePresenterImp
import com.example.mostafahussien.practicekotlin2.data.Config
import com.example.mostafahussien.practicekotlin2.data.RecipeDataManager
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipes
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import io.reactivex.functions.Predicate
import io.reactivex.functions.Function

import javax.inject.Inject

/**
 * Created by Mostafa Hussien on 03/11/2018.
 */
class SearchPresenterImp <V : SearchView> @Inject constructor(
        private val dataManager: RecipeDataManager,
        private val compositeDisposable: CompositeDisposable
) : BasePresenterImp<V>(dataManager, compositeDisposable),SearchPresenter<V> {
    var page = 1
    var query = ""

    override fun search(subject: PublishSubject<String>) {      //PublishSubject emits all the subsequent items of the source Observable at the time of subscription
        compositeDisposable.add(
                subject.debounce(300, TimeUnit.MILLISECONDS)       // Debounce operators emits items only when a specified timespan is passed, you are only interested in receiving them in timely manner.
                        .filter(Predicate { it: String ->
                            return@Predicate it.isNotEmpty()
                        })
                        .distinctUntilChanged()                                                     // filters an Observable by only allowing items through that have not already been emitted.
                        .switchMap(Function<String, ObservableSource<Recipes>> { it ->
                            query = it
                            return@Function dataManager.searchRecipes(
                                    Config.API_KEY,
                                    it,
                                    "1"
                            ).map { recipes ->
                                if (recipes.recipesResult != null) {
                                    for (recipe in recipes.recipesResult!!) {
                                        recipe.liked = dataManager.selectRecipe(recipe.recipeId).size > 0
                                    }
                                }
                                recipes
                            }
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                        })
                        .retry()
                        .subscribe({ recipes ->
                            if (!isViewAttatched())
                                return@subscribe
                            if (recipes.recipesResult != null) {
                                baseView?.refreshSearchList(recipes.recipesResult!!)
                                page = 2
                            }
                        }, { throwable ->
                            if (!isViewAttatched())
                                return@subscribe
                            baseView?.showMessage("Error, try again")
                        })
        )
    }

    override fun getNextPage() {
        compositeDisposable.add(
                dataManager.searchRecipes(
                        Config.API_KEY,
                        query,
                        page.toString()
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ recipes ->
                            if (!isViewAttatched())
                                return@subscribe
                            if (recipes.recipesResult != null) {
                                baseView?.updateRecipeList(recipes.recipesResult!!)
                                ++page
                            }
                        }, { throwable ->
                            if (!isViewAttatched())
                                return@subscribe
                            baseView?.showMessage("Error, try again")
                            baseView?.showErrorInRecyclerView()
                        })
        )
    }

    override fun saveRecipe(recipe: Recipe?) {
        compositeDisposable.addAll(
                Observable.defer { Observable.just(dataManager.insertRecipe(recipe)) }
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ _ ->
                            if (!isViewAttatched())
                                return@subscribe
                            baseView?.showMessage("Recipe Liked.")
                        }, { throwable ->
                            if (!isViewAttatched())
                                return@subscribe
                            baseView?.showMessage("Error, try again")
                        })
        )
    }
}