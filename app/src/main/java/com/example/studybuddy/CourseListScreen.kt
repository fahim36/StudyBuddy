package com.example.studybuddy

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studybuddy.StudyBuddyViewModel
import dagger.hilt.android.AndroidEntryPoint


@Composable
fun CourseListScreen(navController: NavHostController, viewModel: StudyBuddyViewModel) {

    val isSuccess = viewModel.isSuccess.observeAsState()
    if (isSuccess.value == true) {
        Utils.showToast(LocalContext.current, "Post Successfully done")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Course List", style = TextStyle(fontSize = 24.sp))
        Icon(
            Icons.Filled.Menu,
            contentDescription = "Menu Icon",
            modifier = Modifier.clickable {
                viewModel.postDataToFirebase(
                    "Example Course",
                    "Example Location",
                    "Example Date",
                    listOf("Example Member1", "Example Member2")
                )
            })
    }

}
