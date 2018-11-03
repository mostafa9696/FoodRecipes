package com.example.mostafahussien.practicekotlin2.UI.likes

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mostafahussien.practicekotlin2.R
import com.example.mostafahussien.practicekotlin2.UI.RecipeDiffUtilCallback
import com.example.mostafahussien.practicekotlin2.data.network.model.recipes.Recipe
import kotlinx.android.synthetic.main.recipe_list_item_saved.view.*

/**
 * Created by Mostafa Hussien on 26/10/2018.
 */
class LikeAdapter(private val likeList : MutableList<Recipe>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var callback: Callback? = null


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LikeViewHolder).onBind(likeList?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = LikeViewHolder(LayoutInflater.from(parent?.context).inflate(
                R.layout.recipe_list_item_saved,
                parent,
                false
        ))
        viewHolder.itemView.like_image_card.setOnClickListener{
            val recipe = getItem(viewHolder.adapterPosition)
            if(recipe != null)
                callback?.onSingleClick(recipe.sourceUrl)
        }

        viewHolder.itemView.delete_button.setOnClickListener({
            val recipe = getItem(viewHolder.adapterPosition)
            if (recipe != null) {
                callback?.onRemoveRecipeClick(recipe)
            }
        })

        viewHolder.itemView.share_button.setOnClickListener({
            val recipe = getItem(viewHolder.adapterPosition)
            if (recipe != null) {
                callback?.onShareClick(recipe.sourceUrl)
            }
        })

        viewHolder.itemView.ingredients_button.setOnClickListener({
            val recipe = getItem(viewHolder.adapterPosition)
            if (recipe != null) {
                callback?.onIngredientsClick(recipe.recipeId)
            }
        })

        return viewHolder
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is LikeViewHolder) {
            holder.itemView.recipe_fav_title.text = ""
            holder.itemView.recipe_fav_image.setImageDrawable(null)
        }
    }

    override fun getItemCount(): Int {
        return likeList?.size ?:0
    }

    fun getItem(position: Int): Recipe? {
        return if (position != RecyclerView.NO_POSITION)
            likeList?.get(position)
        else
            null
    }

    class LikeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(recipe: Recipe?) {
            Glide
                    .with(itemView.context)
                    .load(recipe?.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemView.recipe_fav_image)
            itemView.recipe_fav_title.text = recipe?.title
        }
    }

    fun addItems(recipeList: MutableList<Recipe>?) {
        val newLikeList = ArrayList<Recipe>()
        newLikeList.addAll(recipeList!!)
        val diffResult =
                DiffUtil.calculateDiff(
                        RecipeDiffUtilCallback(
                                this.likeList,
                                newLikeList
                        )
                )
        this.likeList?.clear()
        this.likeList?.addAll(recipeList)
        diffResult.dispatchUpdatesTo(this)
    }

    interface Callback {
        fun onRemoveRecipeClick(recipe: Recipe)

        fun onShareClick(sourceUrl: String)

        fun onIngredientsClick(recipeId: String)

        fun onSingleClick(sourceUrl: String)
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }
}