package com.example.mostafahussien.practicekotlin2.UI.recipes

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter

import com.example.mostafahussien.practicekotlin2.R
import com.example.mostafahussien.practicekotlin2.UI.Base.BaseFragment
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_recipes.*
import kotlinx.android.synthetic.main.ingredients_dialog_view.view.*
import kotlinx.android.synthetic.main.recipes_list_view_footer.view.*
import javax.inject.Inject

class RecipesFragment : BaseFragment(), RecipesView, RecipeAdapter.AdapterCallback {

    @Inject
    lateinit var presenter : RecipePresenter<RecipesView>
    @Inject
    lateinit var recipesAdapter: RecipeAdapter
    @Inject
    lateinit var linearLayoutManger : LinearLayoutManager
    @Inject
    lateinit var recipesAnimation : RecipesItemAnimator
    @Inject
    lateinit var customTabsIntent: CustomTabsIntent         // for open webview
    var isLoading = false


    companion object {
        fun newInstance() = RecipesFragment()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)
        presenter.onAttach(this)
        recipesAdapter.setCallback(this)
        return view
    }

    override fun setUp(view: View) {

        recipeList.layoutManager = linearLayoutManger
        recipeList.itemAnimator = recipesAnimation
        recipeList.adapter = recipesAdapter

        recipeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val visibleItemCount = linearLayoutManger.childCount
                val totalItemCount = linearLayoutManger.itemCount
                val pastVisibleItems =  linearLayoutManger.findFirstCompletelyVisibleItemPosition()

                if(visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading){
                    recipesAdapter.addItem(
                            Recipe(
                                    type = "LOADING"
                            )
                    )
                    presenter.getRecipes()
                    isLoading = true
                }
            }
        })
        showLoading()
        presenter.getRecipes()
    }
    // Adapter callback

    override fun onLikeRecipeClick(position: Int) {
        presenter.saveRecipe(recipesAdapter.getItem(position))
    }

    override fun onRetryClick() {
        presenter.getRecipes()
    }

    override fun onShareClick(sourceUrl: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, sourceUrl)
        shareIntent.type = "text/plain"
        startActivity(Intent.createChooser(shareIntent, "Share using..."))
    }

    override fun onIngredientsClick(recipeId: String) {
        presenter.getRecipe(recipeId)
    }

    override fun onSingleClick(sourceUrl: String) {
        customTabsIntent.launchUrl(activity, Uri.parse(sourceUrl))
    }

    override fun updateRecipesList(recipes: MutableList<Recipe>) {
       if(recipesAdapter.itemCount > 0)         // loading view is exist
           recipesAdapter.removeItem()
        recipesAdapter.addItems(recipes)
        isLoading = false
    }

    override fun showIngredients(ingredients: List<String>?) {
        val layoutInflater =
                activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.ingredients_dialog_view, null, false)
        view.title.text = "Ingredients"
        view.ingredients_list.adapter = ArrayAdapter(activity, R.layout.ingredients_list_item,ingredients)
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)
        dialog.window.attributes.windowAnimations = R.style.DialogTheme
        dialog.show()

    }

    override fun showRecyclerError() {
        val loadingViewHolder = recipeList.findViewHolderForAdapterPosition(recipesAdapter.itemCount-1) as RecipeAdapter.LoadingMoreViewHolder?
        if(loadingViewHolder != null){
            loadingViewHolder.itemView.loading.visibility = View.GONE
            loadingViewHolder.itemView.retry_button.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        presenter.onDeAttach()
        super.onDestroyView()
    }


}
