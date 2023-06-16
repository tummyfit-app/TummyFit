package com.capstoneproject.tummyfit.ui.recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.food.FoodsItem
import com.capstoneproject.tummyfit.databinding.ItemListMealHorizontalBinding
import com.capstoneproject.tummyfit.utils.callbackFoodDiffUtil

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class FastFoodAdapter : RecyclerView.Adapter<FastFoodAdapter.ListViewHolder>() {

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
        fun bind(item: FoodsItem) {
            binding.apply {
                Glide.with(itemView).load(item.image).placeholder(R.drawable.load_img_error).error(R.drawable.load_img_error)
                    .optionalCenterCrop().into(imageMeal)
                buttonMeal.text = String.format(
                    itemView.resources.getString(R.string.kcal_template),
                    item.calories
                )
                titleMeal.text = item.name
                descOneMeal.text = item.dishType
                descTwoMeal.text = "Fast meals served"
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