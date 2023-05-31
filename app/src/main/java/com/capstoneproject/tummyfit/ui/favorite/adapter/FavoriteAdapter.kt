package com.capstoneproject.tummyfit.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.local.database.entity.FavoriteMealEntity
import com.capstoneproject.tummyfit.data.remote.model.food.FoodsItem
import com.capstoneproject.tummyfit.databinding.ItemIngredientsBinding
import com.capstoneproject.tummyfit.databinding.ItemMealRecommendBinding
import com.capstoneproject.tummyfit.ui.detailmeal.adapter.IngredientAdapter
import com.capstoneproject.tummyfit.ui.home.adapter.TryItAdapter
import com.capstoneproject.tummyfit.utils.callbackFavoriteMealEntityDiffUtil
import com.capstoneproject.tummyfit.utils.callbackStringDiffUtil

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    val differ = AsyncListDiffer(this, callbackFavoriteMealEntityDiffUtil)

    private lateinit var listener: FavoriteAdapter.OnItemClickListener

    fun setOnClickListener(listener: FavoriteAdapter.OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder =
        ListViewHolder(
            ItemMealRecommendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class ListViewHolder(private val binding: ItemMealRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteMealEntity) {
            binding.apply {
                Glide.with(itemView).load(item.image).into(imageMeal)
                buttonMeal.text =
                    String.format(itemView.resources.getString(R.string.kcal_template, item.kcal))
                titleMeal.text = item.name
                descOneMeal.text = item.desc
                root.setOnClickListener {
                    listener.onItemClicked(item)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(item: FavoriteMealEntity)
    }
}