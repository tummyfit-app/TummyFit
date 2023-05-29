package com.capstoneproject.tummyfit.ui.home.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.food.FoodsItem
import com.capstoneproject.tummyfit.databinding.ItemListMealVerticalBinding
import com.capstoneproject.tummyfit.utils.callbackFoodDiffUtil

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class TryItAdapter : RecyclerView.Adapter<TryItAdapter.ListViewHolder>() {

    val differ = AsyncListDiffer(this, callbackFoodDiffUtil)

    private lateinit var listener: OnItemClickListener
    fun setOnClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder =
        ListViewHolder(
            ItemListMealVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class ListViewHolder(private val binding: ItemListMealVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FoodsItem) {
            binding.apply {
//                .apply(
//                RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
//            )
                Glide.with(itemView).load(item.image).into(imageMeal)
                buttonMeal.text = String.format(
                    itemView.resources.getString(R.string.kcal_template),
                    item.calories
                )
                titleMeal.text = item.name
                descOneMeal.text = item.dishType
                descTwoMeal.text = if (item.halal.equals("True", true)) "halal" else "non-halal"
                root.setOnClickListener {
                    listener.onItemClicked(item)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(item: FoodsItem)
    }

}