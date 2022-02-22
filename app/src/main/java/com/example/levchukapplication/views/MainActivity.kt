package com.example.levchukapplication.views

import android.content.Intent
import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.levchukapplication.R
import com.example.levchukapplication.style.orangeMain

class MainActivity : BaseActivity() {
    private val text = mutableStateOf("")
    private val textFromSecondActivity: MutableState<String?> = mutableStateOf(null)
    private val progress = mutableStateOf(0f)
    private var progressCountDownTimer: CountDownTimer? = null

    override val windowName: String = "Main window"

    override fun onResume() {
        super.onResume()
        progressCountDownTimer?.cancel()
        progress.value = 0f
    }



    @Composable
    override fun MainContent() {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = orangeMain)
        ) {
            TextField(
                value = text.value,
                label = { Text(text = "Enter the text you want to pass") },
                onValueChange = { text.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            )
            if (progress.value > 0) LinearProgressIndicator(
                progress = progress.value, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Button(onClick = { countDownToSecondActivityLaunch() }) {
                Text(text = "Send text to second activity")
            }
            Spacer(modifier = Modifier.height(128.dp))
            textFromSecondActivity.value?.let {
                Text(text = "Received text from second activity: $it")
            }

        }
        Text(text = "Made by: ${stringResource(id = R.string.author_name)}, ${stringResource(id = R.string.author_email)}")
    }

    private fun countDownToSecondActivityLaunch() {
        progressCountDownTimer?.cancel()
        progressCountDownTimer = object : CountDownTimer(2500, 100) {
            override fun onFinish() {
                openChildActivity()
            }

            override fun onTick(millisUntilFinished: Long) {
                progress.value += 0.04f
            }

        }
        progressCountDownTimer?.start()
    }

    private fun openChildActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(MAIN_ACTIVITY_EXTRA_TEXT, text.value)
        startActivityForResult(intent, SECONT_ACTIVITY_INFO_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SECONT_ACTIVITY_INFO_REQUEST_CODE) {
            if (resultCode == RESULT_OK)
                textFromSecondActivity.value =
                    data?.getStringExtra(SecondActivity.SECOND_ACTIVITY_EXTRA_TEXT)
            else textFromSecondActivity.value = "SOMETHING FAILED"
        }
    }

    companion object {
        const val MAIN_ACTIVITY_EXTRA_TEXT = "MAIN_ACTIVITY_EXTRA_TEXT"
        const val SECONT_ACTIVITY_INFO_REQUEST_CODE = 4285353
   }
}