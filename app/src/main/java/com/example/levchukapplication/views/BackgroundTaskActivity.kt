package com.example.levchukapplication.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.levchukapplication.services.SimpleAsyncTask
import com.example.levchukapplication.style.Purple200
import com.example.levchukapplication.style.black
import com.example.levchukapplication.style.white

class BackgroundTaskActivity : BaseActivity() {

    override val windowName: String = "Background Task window"
    private val progress = mutableStateOf(0f)
    private val activeText = mutableStateOf("")

    @Preview
    @Composable
    override fun MainContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Purple200)
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = if (progress.value > 0f && progress.value < 100f) "${progress.value}%" else activeText.value,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight()
                    .padding(16.dp)
            )
            if (progress.value > 0f) LinearProgressIndicator(
                progress = progress.value/100f,
                color = white,
                backgroundColor = black
            )
            Spacer(modifier = Modifier.height(32.dp))
            if (progress.value <= 0 || progress.value >= 100) Button(onClick = { launchAsyncTask() }) {
                Text(text = "Launch background task")
            }
        }
    }

    private fun launchAsyncTask() {
        SimpleAsyncTask({
            progress.value = it.toFloat()
        }, { activeText.value = it }).execute()
    }
}