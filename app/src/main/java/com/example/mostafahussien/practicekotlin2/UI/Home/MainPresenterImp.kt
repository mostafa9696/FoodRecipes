package com.example.mostafahussien.practicekotlin2.UI.Home

import com.example.mostafahussien.practicekotlin2.data.RecipeDataManager
import com.example.mostafahussien.practicekotlin2.UI.Base.BasePresenterImp
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
class MainPresenterImp <V : MainView> @Inject constructor(
        private val dataManager: RecipeDataManager,
        private val compositeDisposable: CompositeDisposable
)   : BasePresenterImp<V>(dataManager, compositeDisposable), MainPresenter<V> {

}