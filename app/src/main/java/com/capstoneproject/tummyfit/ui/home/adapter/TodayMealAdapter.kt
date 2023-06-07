package com.capstoneproject.tummyfit.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.food.MenuItem
import com.capstoneproject.tummyfit.data.remote.model.food.PredictionItem
import com.capstoneproject.tummyfit.databinding.ItemListMealHorizontalBinding
import com.capstoneproject.tummyfit.utils.callbackFoodPredictionDiffUtil

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class TodayMealAdapter : RecyclerView.Adapter<TodayMealAdapter.ListViewHolder>() {

    val differ = AsyncListDiffer(this, callbackFoodPredictionDiffUtil)

    private lateinit var listener: OnItemClickListener
    fun setOnClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder =
        ListViewHolder(
            ItemListMealHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class ListViewHolder(private val binding: ItemListMealHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuItem) {
            binding.apply {
                Glide.with(imageMeal).load(item.image).error(R.drawable.load_img_error).optionalCenterCrop().into(imageMeal)
                titleMeal.text = item.recipeTitle
                buttonMeal.text = String.format(
                    itemView.resources.getString(R.string.kcal_template),
                    item.calories
                )
                descOneMeal.text = item.category
                descTwoMeal.text = if (item.halal.equals("True", true)) "halal" else "non-halal"
                root.setOnClickListener {
                    listener.onItemClicked(item)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(item: MenuItem)
    }

}