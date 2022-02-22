package com.example.levchukapplication.views

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.levchukapplication.BuildConfig
import com.example.levchukapplication.R
import com.example.levchukapplication.services.MessageBroadcastReceiver
import com.example.levchukapplication.style.yellowFilter

class LocalBroadcastActivity : BaseActivity() {

    override val windowName: String = "Local Broadcast window"
    private val text = mutableStateOf("")

    private val broadcastReceiver = MessageBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalBroadcastManager.getInstance(this).registerReceiver(
            broadcastReceiver, IntentFilter(
                ACTION_CUSTOM_BROADCAST
            )
        )
    }

    override fun onDestroy() {
        this.unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    @Preview
    @Composable
    override fun MainContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(yellowFilter)
                .padding(top=64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            TextField(
                value = text.value,
                label = { Text(text = "Enter the text you want to broadcast") },
                onValueChange = { text.value = it },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { broadcastMessage() }) {
                Text(text = "Broadcast text")
            }
        }
    }

    private fun broadcastMessage() {
        val intent = Intent(ACTION_CUSTOM_BROADCAST)
        intent.putExtra(CUSTOM_BROADCAST_MESSAGE_TAG, text.value)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    companion object {
        const val ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST"

        const val CUSTOM_BROADCAST_MESSAGE_TAG = "CUSTOM_BROADCAST_MESSAGE_TAG"
    }
}