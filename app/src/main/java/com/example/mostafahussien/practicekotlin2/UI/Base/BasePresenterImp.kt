package com.example.mostafahussien.practicekotlin2.UI.Base

import com.example.mostafahussien.practicekotlin2.data.RecipeDataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
open class BasePresenterImp <V: BaseView> @Inject constructor (
        private val recipeDataManager: RecipeDataManager,
        private val compositeDisposable: CompositeDisposable
)  : BasePresenter<V> {

    var baseView: V?=null

    override fun onAttach(baseView: V) {
        this.baseView=baseView
    }

    override fun onDeAttach() {
        compositeDisposable.dispose()
        this.baseView=null
    }

    fun isViewAttatched () = baseView!=null

    fun checkViewAttatched(){
        if(!isViewAttatched())
            throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException :
            RuntimeException("Please call Presenter.onAttach(MvpView) before" + " requesting data to the Presenter")

}