package com.example.studybuddy

import android.app.Application
import com.google.firebase.FirebaseApp

class StudyBuddyApp : Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}