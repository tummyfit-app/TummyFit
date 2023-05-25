package com.capstoneproject.tummyfit.utils

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
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

fun scoreIbm(weight: Int, height: Int): Double = (weight / ((height * 0.01) * (height * 0.01))).roundToInt()
    .toDouble()


fun showSnackbar(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        .show()
}