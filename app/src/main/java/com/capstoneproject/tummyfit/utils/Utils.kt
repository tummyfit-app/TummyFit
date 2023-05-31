package com.capstoneproject.tummyfit.utils

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import com.capstoneproject.tummyfit.data.local.database.entity.FavoriteMealEntity
import com.capstoneproject.tummyfit.data.remote.model.food.FoodsItem
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

fun showDatePicker(fragmentManager: FragmentManager, textInputEditText: TextInputEditText) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTextInputFormat(dateFormat)
            .build()
    datePicker.show(fragmentManager, "DATE_DIALOG")
    datePicker.addOnPositiveButtonClickListener {
        textInputEditText.setText(dateFormat.format(Date(it)))
    }
    datePicker.addOnNegativeButtonClickListener {
        datePicker.dismiss()
    }
    datePicker.addOnCancelListener {
        datePicker.dismiss()
    }
}

fun scoreIbm(weight: Int, height: Int): Double =
    (weight / ((height * 0.01) * (height * 0.01))).roundToInt()
        .toDouble()


fun showSnackbar(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        .show()
}

val callbackFoodDiffUtil = object : DiffUtil.ItemCallback<FoodsItem>() {
    override fun areItemsTheSame(
        oldItem: FoodsItem,
        newItem: FoodsItem
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: FoodsItem,
        newItem: FoodsItem
    ): Boolean =
        oldItem == newItem
}

val callbackStringDiffUtil = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean =
        oldItem == newItem
}

val callbackFavoriteMealEntityDiffUtil = object : DiffUtil.ItemCallback<FavoriteMealEntity>() {
    override fun areItemsTheSame(
        oldItem: FavoriteMealEntity,
        newItem: FavoriteMealEntity
    ): Boolean =
        oldItem.id_meal == newItem.id_meal

    override fun areContentsTheSame(
        oldItem: FavoriteMealEntity,
        newItem: FavoriteMealEntity
    ): Boolean =
        oldItem == newItem
}