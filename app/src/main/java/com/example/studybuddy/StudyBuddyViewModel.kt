package com.example.studybuddy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudyBuddyViewModel @Inject constructor() : ViewModel() {
    val user : MutableLiveData<FirebaseUser> = MutableLiveData()
    private val _isSuccess : MutableLiveData<Boolean> = MutableLiveData()
    val isSuccess : LiveData<Boolean> = _isSuccess
    val isEmpty : MutableLiveData<Boolean> = MutableLiveData()
    val db = Firebase.firestore
    fun postDataToFirebase(courseTitle: String, location: String, date: String, members: List<String>) {
        user.value?.uid?.let { uid ->
            val postData = mapOf(
                "title" to courseTitle,
                "location" to location,
                "date" to date,
                "members" to members
            )
            //todo
            val userCollectionRef = db.document("course-list").collection(uid)
            userCollectionRef.add(postData).addOnCompleteListener{
                _isSuccess.value =  it.isSuccessful
            }
        }
    }
}