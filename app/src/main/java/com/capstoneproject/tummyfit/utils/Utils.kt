package com.capstoneproject.tummyfit.utils

import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import com.capstoneproject.tummyfit.data.local.database.entity.FavoriteMealEntity
import com.capstoneproject.tummyfit.data.local.database.entity.WaterIntakeEntity
import com.capstoneproject.tummyfit.data.remote.model.food.FoodsItem
import com.capstoneproject.tummyfit.utils.receiver.AlarmReceiver
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import java.text.DateFormat
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

fun showTimePickerNotification(fragmentManager: FragmentManager, context: Context) {
    val timePicker = MaterialTimePicker.Builder().setInputMode(INPUT_MODE_CLOCK)
        .setHour(Calendar.getInstance().get(Calendar.HOUR))
        .setMinute(Calendar.getInstance().get(Calendar.MINUTE))
        .setTitleText("Set Time").build()
    timePicker.show(fragmentManager, "TIME_DIALOG")
    timePicker.addOnPositiveButtonClickListener {
        AlarmReceiver().setOneTimeAlarm(
            context,
            AlarmReceiver.TYPE_ONE_TIME,
            getCurrentDate(),
            "${timePicker.hour}:${timePicker.minute}",
            "Hey… It's time to drink some water…"
        )
    }

    timePicker.addOnNegativeButtonClickListener {
        timePicker.dismiss()
    }
    timePicker.addOnCancelListener {
        timePicker.dismiss()
    }
}

fun scoreIbm(weight: Int, height: Int): Double =
    (weight / ((height * 0.01) * (height * 0.01))).roundToInt()
        .toDouble()


fun showSnackbar(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        .show()
}

fun getCurrentDate(): String {
    val c = Calendar.getInstance().time
    val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return df.format(c)
}

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = format.parse(this) as Date
    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
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

val callbackWaterIntakeDiffUtil = object : DiffUtil.ItemCallback<WaterIntakeEntity>() {
    override fun areItemsTheSame(
        oldItem: WaterIntakeEntity,
        newItem: WaterIntakeEntity
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: WaterIntakeEntity,
        newItem: WaterIntakeEntity
    ): Boolean =
        oldItem == newItem
}