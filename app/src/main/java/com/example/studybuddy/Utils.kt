package com.example.studybuddy

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {


    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    fun convertMillisToTime(millis: Long): String {
        val formatter = SimpleDateFormat("hh:mm aa", Locale.getDefault())
        return formatter.format(Date(millis))
    }
}
