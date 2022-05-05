package com.example.levchukapplication.views

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levchukapplication.R
import com.example.levchukapplication.style.orangeWarning

class FragmentsActivity : BaseActivity(), FragmentToActivity {

    override val windowName: String = "Activity with demonstrative fragments"
    private val messageForFragment = mutableStateOf("")
    private val textFromMessageFragment: MutableState<String?> = mutableStateOf(null)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                reloadContent()
            }
        }
    }


    @Preview
    @Composable
    override fun MainContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(orangeWarning)
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            textFromMessageFragment.value?.let {
                Text(text = "Received text from message fragment: $it")
            }
            TextField(
                value = messageForFragment.value,
                label = { Text(text = "Enter the text you want to send to the message fragment") },
                onValueChange = { messageForFragment.value = it },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { openFragmentWithMessage(messageForFragment.value) },
                enabled = messageForFragment.value.isNotBlank()
            ) {
                Text(text = "Send text to message fragment\uD83D\uDCAC")
            }

            TextButton(
                onClick = { openLocationFragment() },
                modifier = Modifier.padding(top = 64.dp)
            ) {
                Text(
                    text = "Open location fragment\uD83D\uDDFA️",
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }

    /**
     * In Compose Ui fragments are obsolete, but since it is required by the task, I have to adapt.
     * This function changes root view to an xml layout, so that fragment could be attached.
     * Later, by using fun reloadContent(), I again reset root view to Composable function.
     */
    private fun openFragmentWithMessage(message: String) {
        setContentView(R.layout.activity_main)
        val fragment = MessageFragment.newInstance(message)
        supportFragmentManager.beginTransaction()
            .addToBackStack("messageFragment")
            .replace(R.id.main_container, fragment)
            .commitAllowingStateLoss()
    }

    private fun openLocationFragment() {
        setContentView(R.layout.activity_main)
        val fragment = LocationFragment()
        supportFragmentManager.beginTransaction()
            .addToBackStack("locationFragment")
            .replace(R.id.main_container, fragment)
            .commitAllowingStateLoss()
    }

    override fun communicate(comm: String?) {
        if (comm != null) {
            textFromMessageFragment.value = comm
        }
    }

}