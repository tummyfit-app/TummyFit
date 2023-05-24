package com.capstoneproject.tummyfit.utils

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

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

fun scoreIbm(weight: Int, height: Int, gender: String): Double {
    return if (gender.equals("male", true)) {
        (height - 100) - (0.1 * (height - 100))
    } else {
        (height - 100) - (0.15 * (height - 100))
    }
}