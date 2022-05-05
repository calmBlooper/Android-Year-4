package com.example.levchukapplication.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
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
import com.example.levchukapplication.style.greenMain
import com.example.levchukapplication.style.orangeMain

class MessageFragment : BaseFragment() {
    override val fragmentName: String = "Message fragment"
    private val messageForActivity = mutableStateOf("")
    private val messageFromActivity = mutableStateOf("")
    private var mCallback: FragmentToActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messageFromActivity.value = arguments?.getString("message") ?: ""
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {


            mCallback = requireActivity() as FragmentToActivity
        } catch (e: Exception) {
            Log.i("Stuff", "${requireActivity()} must implement FragmentToActivity")
        }
    }

    override fun onDetach() {
        mCallback = null
        super.onDetach()
    }

    private fun sendData(comm: String) {
        mCallback?.communicate(comm)
        mCallback?.let {
            Toast.makeText(
                requireContext(),
                "Message to the fragment activity successfully sent!!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @Preview
    @Composable
    override fun MainContent() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(orangeMain)
        ) {
            TextField(
                value = messageForActivity.value,
                label = { Text(text = "Enter the text you want to pass to the fragment activity") },
                onValueChange = { messageForActivity.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            )
            Button(onClick = { sendData(messageForActivity.value) }) {
                Text(text = "Send text to the fragment activity")
            }
            Image(
                painter = painterResource(id = R.drawable.gran_turismo),
                contentDescription = stringResource(
                    id = R.string.lyrics
                ),
                modifier = Modifier
                    .size(250.dp)
                    .padding(16.dp)
            )
            Text(
                text = "Text from fragment demonstration activity: ${messageFromActivity.value}",
                modifier = Modifier.background(greenMain)
            )
        }
    }

    companion object {

        fun newInstance(message: String): MessageFragment {
            val fragment = MessageFragment()
            val args = Bundle()
            args.putString("message", message)
            fragment.arguments = args
            return fragment
        }
    }
}

interface FragmentToActivity {
    fun communicate(comm: String?)
}