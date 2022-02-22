package com.example.levchukapplication.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.levchukapplication.views.LocalBroadcastActivity
import com.example.levchukapplication.views.LocalBroadcastActivity.Companion.CUSTOM_BROADCAST_MESSAGE_TAG

class MessageBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            LocalBroadcastActivity.ACTION_CUSTOM_BROADCAST -> {
                val message = intent.getStringExtra(CUSTOM_BROADCAST_MESSAGE_TAG)
                Toast.makeText(
                    context,
                    "Broadcast message received!\nMessage: $message",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                Toast.makeText(context, "Unexpected broadcast message received!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}