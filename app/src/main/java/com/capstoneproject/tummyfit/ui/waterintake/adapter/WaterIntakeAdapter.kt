package com.capstoneproject.tummyfit.ui.waterintake.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.tummyfit.data.local.database.entity.WaterIntakeEntity
import com.capstoneproject.tummyfit.databinding.ItemHistoryWaterBinding
import com.capstoneproject.tummyfit.utils.callbackWaterIntakeDiffUtil
import com.capstoneproject.tummyfit.utils.withDateFormat

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class WaterIntakeAdapter : RecyclerView.Adapter<WaterIntakeAdapter.ListViewHolder>() {

    val differ = AsyncListDiffer(this, callbackWaterIntakeDiffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder =
        ListViewHolder(
            ItemHistoryWaterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class ListViewHolder(private val binding: ItemHistoryWaterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WaterIntakeEntity) {
            binding.apply {
                waterWave.setAnimationSpeed(50)
                waterWave.progress = if (item.now_intake > item.total_intake) 100 else item.now_intake * 100 / item.total_intake
                waterWave.max = 100
                waterWave.startAnimation()
                tvDate.text = item.date.withDateFormat()
                tvWater.text = "${item.now_intake}/${item.total_intake} ml"
            }
        }
    }

}