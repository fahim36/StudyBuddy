package com.example.studybuddy

import com.google.gson.annotations.SerializedName

data class Course(
    @SerializedName("title") val title: String,
    @SerializedName("location") val location: String,
    @SerializedName("date") val date: String,
    @SerializedName("users") val users: String
)