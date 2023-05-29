package com.capstoneproject.tummyfit.ui.detailmeal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.tummyfit.databinding.ItemIngredientsBinding
import com.capstoneproject.tummyfit.utils.callbackStringDiffUtil

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.ListViewHolder>() {

    val differ = AsyncListDiffer(this, callbackStringDiffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder =
        ListViewHolder(
            ItemIngredientsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class ListViewHolder(private val binding: ItemIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.apply {
                btnIngredients.text = item
            }
        }
    }
}