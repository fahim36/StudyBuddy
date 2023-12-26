package com.example.studybuddy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
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
    val isEmpty: MutableLiveData<Boolean> = MutableLiveData()
    val db = Firebase.firestore

    fun getStudyBuddyData(){
        val courseListData = mutableListOf<Course>()
        CoroutineScope(Dispatchers.IO).launch {
            val db = Firebase.firestore
            val docRef = user.value?.uid?.let { db.collection("study-buddy").document(it) }
            docRef?.get()?.addOnSuccessListener { documentSnapshot ->
                documentSnapshot.data?.values?.forEach{
                   val course = Gson().fromJson(Gson().toJson(it), Course::class.java)
                    courseListData.add(course)
                }
                courseList.postValue(courseListData)
            }
        }
    }

    fun postDataToFirebase(
        courseTitle: String,
        location: String,
        date: String,
        members: String
    ) {
        user.value?.uid?.let { uid ->
            val postData = Course(courseTitle,location,date,members)
            val courseData = mapOf(
                courseTitle to postData
            )
            db.collection("study-buddy").document(uid).update(courseData).addOnCompleteListener {
                if (!it.isSuccessful) {
                    db.collection("study-buddy").document(uid).set(courseData)
                        .addOnCompleteListener {
                            _isSuccess.value = it.isSuccessful
                        }
                } else {
                    _isSuccess.value = it.isSuccessful
                }
            }
        }
    }
}