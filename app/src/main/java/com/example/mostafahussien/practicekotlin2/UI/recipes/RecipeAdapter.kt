package com.example.mostafahussien.practicekotlin2.UI.recipes

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mostafahussien.practicekotlin2.NetworkUtils
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.mostafahussien.practicekotlin2.R
import com.example.mostafahussien.practicekotlin2.UI.RecipeDiffUtilCallback
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe
import kotlinx.android.synthetic.main.recipe_list_item.view.*
import kotlinx.android.synthetic.main.recipes_list_view_footer.view.*
import java.util.logging.Handler

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
class RecipeAdapter(private val recipeList: MutableList<Recipe>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        val ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button"
        val ACTION_LIKE_IMAGE_DOUBLE_CLICKED = "action_like_image_button"
    }

    private val TYPE_LOADING = -1
    private val TYPE_RECIPE = 1

    private var tapCount = 0
    private var callback: AdapterCallback? = null



    override fun getItemCount(): Int {
        return recipeList?.size ?: 0

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_RECIPE ->
                createRecipeViewHolder(parent)
            else ->
                createLoadingMoreViewHolder(parent)
        }
    }

    private fun createRecipeViewHolder(parent: ViewGroup) :  RecyclerView.ViewHolder{
        val recipeViewHolder = RecipeViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.recipe_list_item,parent,false))
        recipeViewHolder.itemView.recipe_image_card.setOnClickListener {
            val recipe = getItem(recipeViewHolder.adapterPosition)
            if(recipe != null){
                tapCount++
                if(tapCount == 1){
                    android.os.Handler().postDelayed({
                        if(tapCount == 1){
                            callback?.onSingleClick(recipe.sourceUrl)
                        }
                        tapCount = 0
                    }, 250)
                } else if(tapCount == 2) {
                    tapCount = 0
                    onDoubleClick(recipeViewHolder.adapterPosition, recipe)
                }
            }
        }

        recipeViewHolder.itemView.like_button.setOnClickListener({
            val recipe = getItem(recipeViewHolder.adapterPosition)
            if (recipe != null) {
                notifyItemChanged(recipeViewHolder.adapterPosition, ACTION_LIKE_BUTTON_CLICKED)
                callback?.onLikeRecipeClick(recipeViewHolder.adapterPosition)
                recipe.liked = true
            }
        })

        recipeViewHolder.itemView.share_button.setOnClickListener({
            val recipe = getItem(recipeViewHolder.adapterPosition)
            if (recipe != null) {
                callback?.onShareClick(recipe.sourceUrl)
            }
        })

        recipeViewHolder.itemView.ingredients_button.setOnClickListener({
            val recipe = getItem(recipeViewHolder.adapterPosition)
            if (recipe != null) {
                callback?.onIngredientsClick(recipe.recipeId)
            }
        })

        return recipeViewHolder
    }

    private fun createLoadingMoreViewHolder(parent: ViewGroup?): LoadingMoreViewHolder {
        val loadingMoreViewHolder = LoadingMoreViewHolder(LayoutInflater.from(parent?.context).inflate(
                        R.layout.recipes_list_view_footer, parent, false)
        )

        loadingMoreViewHolder.itemView.retry_button.setOnClickListener({
            loadingMoreViewHolder.itemView.loading.visibility = View.VISIBLE
            loadingMoreViewHolder.itemView.retry_button.visibility = View.GONE
            callback?.onRetryClick()
        })

        return loadingMoreViewHolder
    }

    private fun onDoubleClick(adapterPosition: Int, recipe: Recipe) {
        notifyItemChanged(adapterPosition, ACTION_LIKE_IMAGE_DOUBLE_CLICKED)
        callback?.onLikeRecipeClick(adapterPosition)
        recipe.liked = true
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is RecipeViewHolder) {
            holder.itemView.recipe_title.text = ""
            holder.itemView.recipe_image.setImageDrawable(null)
            holder.itemView.like_button.setImageResource(R.drawable.ic_favorite_border_white_24dp)
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is LoadingMoreViewHolder) {
            if (!NetworkUtils.isNetworkConnected(holder.itemView.context)) {
                holder.itemView.loading.visibility = View.GONE
                holder.itemView.retry_button.visibility = View.VISIBLE
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            TYPE_RECIPE -> (holder as RecipeViewHolder).onBind(recipeList?.get(position))
            TYPE_LOADING -> (holder as LoadingMoreViewHolder).onBind()
        }
    }

    fun addItems(newRecipeList: MutableList<Recipe>?){
        val tmpRecipeList = ArrayList<Recipe>()
        tmpRecipeList.addAll(this.recipeList!!)
        tmpRecipeList.addAll(newRecipeList!!)

        val diffResultItems = DiffUtil.calculateDiff(
                RecipeDiffUtilCallback(
                        this.recipeList,
                        tmpRecipeList
                )
        )
        this.recipeList.addAll(newRecipeList)
        diffResultItems.dispatchUpdatesTo(this)         //  it will invoke adapter’s onBindViewHolder(…) method
    }

    fun addItem(recipe: Recipe?){
        recipeList?.add(recipe!!)
        notifyItemInserted(recipeList!!.size-1)
    }

    fun removeItem(){
        recipeList?.removeAt(recipeList!!.size-1)
        notifyItemRemoved(recipeList!!.size)
    }

    override fun getItemViewType(position: Int): Int {
        return if (recipeList?.get(position)?.type == "LOADING") TYPE_LOADING else TYPE_RECIPE
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getItem(position: Int): Recipe? {
        return if (position != RecyclerView.NO_POSITION)
            recipeList?.get(position)
        else
            null
    }

    class RecipeViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(recipe : Recipe?){
            Glide.with(itemView.context)
                    .load(recipe?.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemView.recipe_image)
            itemView.recipe_title.text = recipe?.title
            if (recipe?.liked!!)
                itemView.like_button.setImageResource(R.drawable.ic_favorite_higlighted_24dp)
            else
                itemView.like_button.setImageResource(R.drawable.ic_favorite_border_white_24dp)
        }
    }

    class LoadingMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind() {
        }
    }

    fun setCallback(callback: AdapterCallback) {
        this.callback = callback
    }
    interface AdapterCallback {
        fun onLikeRecipeClick(position: Int)

        fun onRetryClick()

        fun onShareClick(sourceUrl: String)

        fun onIngredientsClick(recipeId: String)

        fun onSingleClick(sourceUrl: String)
    }
}