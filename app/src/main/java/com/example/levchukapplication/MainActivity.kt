package com.example.levchukapplication

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levchukapplication.style.orangeMain

class MainActivity : AppCompatActivity() {
    private val text = mutableStateOf("")
    private val textFromSecondActivity: MutableState<String?> = mutableStateOf(null)
    private val progress = mutableStateOf(0f)
    private var progressCountDownTimer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    override fun onResume() {
        super.onResume()
        progressCountDownTimer?.cancel()
        progress.value = 0f
    }

    @Preview
    @Composable
    fun Content() {
//        val scaffoldState = rememberScaffoldState()
//        scaffoldState.drawerState.open()
        Scaffold(
            topBar = {
                TopAppBar { /* Top app bar content */
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Main window", fontSize = 24.sp)
                        Button(onClick = {}) {
                            Text("Help")
                        }
                    }
                }
            },
            drawerContent = {
                Text("Main window", modifier = Modifier.padding(16.dp))

                Divider()
                Text("Help", modifier = Modifier.padding(16.dp))

                Divider()
                Text("Background task window", modifier = Modifier.padding(16.dp))

                Divider()
                Text("Local broadcast window", modifier = Modifier.padding(16.dp))

                Divider()
                // Drawer items
            }//,
         //   scaffoldState = ScaffoldState(DrawerState(DrawerValue.Open), SnackbarHostState())
        ) {
            // Screen content


            MainContent()
        }
    }

    @Composable
    fun MainContent() {

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