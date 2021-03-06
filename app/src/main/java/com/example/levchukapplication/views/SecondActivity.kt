package com.example.levchukapplication.views

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.levchukapplication.R
import com.example.levchukapplication.style.greenFilter
import com.example.levchukapplication.style.pinkText

class SecondActivity : BaseActivity() {
    private var textFromMainActivity = ""
    private val text = mutableStateOf("")
    private val progress = mutableStateOf(0f)
    private var progressCountDownTimer: CountDownTimer? = null
    override val windowName: String = "Second window"
    override fun onCreate(savedInstanceState: Bundle?) {
         textFromMainActivity =
        this.intent.getStringExtra(MainActivity.MAIN_ACTIVITY_EXTRA_TEXT).toString()
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {

    }

    @Preview
    @Composable
   override fun MainContent() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = greenFilter)
        ) {
            TextField(
                value = text.value,
                label = { Text(text = "Enter the text you want to pass to main activity") },
                onValueChange = { text.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            )
            if (progress.value > 0) CircularProgressIndicator(
                progress = progress.value, modifier = Modifier
                    .size(64.dp)
                    .padding(16.dp)
            )
            Button(onClick = { countDownToMainActivityLaunch() }) {
                Text(text = "Send text to main activity")
            }
            Image(
                painter = painterResource(id = R.drawable.best_image_in_da_world),
                contentDescription = stringResource(
                    id = R.string.lyrics
                ),
                modifier = Modifier
                    .size(250.dp)
                    .padding(16.dp)
            )
            Text(
                text = "Text from main activity: $textFromMainActivity",
                modifier = Modifier.background(pinkText)
            )
        }

    }

    private fun countDownToMainActivityLaunch() {
        progressCountDownTimer?.cancel()
        progressCountDownTimer = object : CountDownTimer(2500, 100) {
            override fun onFinish() {
                openMainActivity()
            }

            override fun onTick(millisUntilFinished: Long) {
                progress.value += 0.04f
            }

        }
        progressCountDownTimer?.start()
    }

    private fun openMainActivity() {
        val intent = Intent()
        intent.putExtra(SECOND_ACTIVITY_EXTRA_TEXT, text.value)
        setResult(RESULT_OK, intent)
        finish()
    }

    companion object {
        const val SECOND_ACTIVITY_EXTRA_TEXT = "SECOND_ACTIVITY_EXTRA_TEXT"
    }
}