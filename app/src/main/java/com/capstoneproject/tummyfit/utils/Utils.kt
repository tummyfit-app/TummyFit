package com.capstoneproject.tummyfit.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import com.capstoneproject.tummyfit.data.local.database.entity.FavoriteMealEntity
import com.capstoneproject.tummyfit.data.local.database.entity.WaterIntakeEntity
import com.capstoneproject.tummyfit.data.remote.model.food.FoodsItem
import com.capstoneproject.tummyfit.data.remote.model.food.MealItem
import com.capstoneproject.tummyfit.utils.receiver.AlarmReceiver
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
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

private const val FILENAME_FORMAT = "yyyy-MM-dd"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = format.parse(this) as Date
    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}

fun getDayFormat(): String {
    val dayNames: Array<String> = DateFormatSymbols().weekdays
    val date = Calendar.getInstance()
    return dayNames[date[Calendar.DAY_OF_WEEK]]
}

fun getChipDayFormat(): String {
    val dayNames: Array<String> = DateFormatSymbols().weekdays
    val date = Calendar.getInstance()
    val dayNow = dayNames[date[Calendar.DAY_OF_WEEK]]
    if (dayNow.equals("Monday", true)) return "Mon"
    else if (dayNow.equals("Tuesday", true)) return "Tue"
    else if (dayNow.equals("Wednesday", true)) return "Wed"
    else if (dayNow.equals("Thursday", true)) return "Thu"
    else if (dayNow.equals("Friday", true)) return "Fri"
    else if (dayNow.equals("Saturday", true)) return "Sat"
    else if (dayNow.equals("Sunday", true)) return "Sun"
    return ""
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

fun imageMultipart(getFile: File): MultipartBody.Part {
    val requestImageFile = getFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(
        "file",
        getFile.name,
        requestImageFile
    )
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

val callbackFoodPredictionDiffUtil = object : DiffUtil.ItemCallback<MealItem>() {
    override fun areItemsTheSame(
        oldItem: MealItem,
        newItem: MealItem
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: MealItem,
        newItem: MealItem
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