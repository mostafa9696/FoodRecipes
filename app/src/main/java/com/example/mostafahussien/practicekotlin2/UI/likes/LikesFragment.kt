package com.example.mostafahussien.practicekotlin2.UI.likes


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter

import com.example.mostafahussien.practicekotlin2.R
import com.example.mostafahussien.practicekotlin2.UI.Base.BaseFragment
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_likes.*
import kotlinx.android.synthetic.main.ingredients_dialog_view.view.*
import javax.inject.Inject


class LikesFragment : BaseFragment(), LikeView, LikeAdapter.Callback {

    @Inject
    lateinit var presenter : LikePresenter<LikeView>
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager
    @Inject
    lateinit var likesAdapter: LikeAdapter
    @Inject
    lateinit var customTabsIntent: CustomTabsIntent

    companion object {
        fun newInstance() = LikesFragment()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : View = inflater.inflate(R.layout.fragment_likes, container, false)
        presenter.onAttach(this)
        likesAdapter.setCallback(this)
        return view
    }

    override fun setUp(view: View) {
        likesList.layoutManager = linearLayoutManager
        likesList.adapter = likesAdapter
        presenter.getRecipes()
    }

    override fun onRemoveRecipeClick(recipe: Recipe) {
        presenter.removeRecipe(recipe)
    }

    override fun onShareClick(sourceUrl: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, sourceUrl)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, "Share using..."))
    }

    override fun onIngredientsClick(recipeId: String) {
        presenter.getRecipe(recipeId)
    }

    override fun onSingleClick(sourceUrl: String) {
        customTabsIntent.launchUrl(activity, Uri.parse(sourceUrl))
    }

    override fun updateLikeList(recipes: MutableList<Recipe>?) {
        likesAdapter.addItems(recipes)
    }

    override fun showIngredients(ingredients: List<String>?) {
        val layoutnflater = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutnflater.inflate(R.layout.ingredients_dialog_view, null, false)
        view.title.text = "Ingredients"
        view.ingredients_list.adapter = ArrayAdapter(activity,R.layout.ingredients_list_item, ingredients)
        val dialog = Dialog(activity)
        dialog.setContentView(view)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.attributes.windowAnimations = R.style.DialogTheme
        dialog.show()
    }

    override fun onDestroyView() {
        presenter.onDeAttach()
        super.onDestroyView()
    }

}
