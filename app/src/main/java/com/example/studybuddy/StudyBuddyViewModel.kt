package com.example.studybuddy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyBuddyViewModel @Inject constructor() : ViewModel() {
    val user: MutableLiveData<FirebaseUser> = MutableLiveData()
    val courseList: MutableLiveData<List<Course>> = MutableLiveData()
    private val _isSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccess: LiveData<Boolean> = _isSuccess
    val isEmpty: MutableLiveData<Boolean> = MutableLiveData(true)
    val db = Firebase.firestore

    fun getStudyBuddyData() {
        val courseListData = mutableListOf<Course>()
        CoroutineScope(Dispatchers.IO).launch {
            val db = Firebase.firestore
            db.collection("study-buddy")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        val data = document.toObject(Course::class.java)
                        if (data != null) {
                            courseListData.add(data)
                        }
                    }
                    courseList.postValue(courseListData)
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error getting documents", e)
                }
        }
    }

    fun postDataToFirebase(
        courseTitle: String,
        location: String,
        date: String,
        time: String,
        members: String
    ) {

        val postData = Course(
            courseTitle,
            location,
            date,
            time,
            members,
            System.currentTimeMillis().toString()
        )
        val courseData = mapOf(
            "title" to postData.title,
            "location" to postData.location,
            "date" to postData.date,
            "time" to postData.time,
            "users" to postData.users,
            "createdAt" to postData.createdAt
        )
        db.collection("study-buddy")
            .add(courseData)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot written with ID: ${documentReference.id}")
                // Handle the success scenario
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
                // Handle the failure scenario
            }
    }
}