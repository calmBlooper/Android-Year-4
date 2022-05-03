package com.example.levchukapplication.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levchukapplication.R
import com.example.levchukapplication.style.black
import com.example.levchukapplication.style.white
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity() {

    abstract val windowName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }


    @Preview
    @Composable
    fun Content() {
        val scaffoldState =
            rememberScaffoldState(DrawerState(DrawerValue.Closed), SnackbarHostState())
        val scope = rememberCoroutineScope()
        Scaffold(
            topBar = {
                TopAppBar { /* Top app bar content */
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = {

                            scope.launch {
                                scaffoldState.drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }

                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_navigation_drawer),
                                contentDescription = "",
                                modifier = Modifier.size(48.dp)
                            )
                        }
                        Text(
                            text = windowName,
                            fontSize = 20.sp,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        Button(onClick = { openHelpActivity() }) {
                            Text("Help")
                        }
                    }
                }
            },
            drawerContent = {
                Text("Main window", modifier = Modifier
                    .clickable {
                        openMainWindowActivity()
                    }
                    .padding(16.dp))

                Divider()
                Text("Help", modifier = Modifier
                    .clickable {
                        openHelpActivity()
                    }
                    .padding(16.dp))

                Divider()
                Text("Background task window", modifier = Modifier
                    .clickable {
                        openBackgroundTaskActivity()
                    }
                    .padding(16.dp))

                Divider()
                Text("Local broadcast window", modifier = Modifier
                    .clickable {
                        openLocalBroadcastActivity()
                    }
                    .padding(16.dp))

                Divider()
                Text("Local custom preferences window", modifier = Modifier
                    .clickable {
                        openCustomPreferencesActivity()
                    }
                    .padding(16.dp))

                Divider()
                // Drawer items
            },
            drawerGesturesEnabled = true,
            //,
            scaffoldState = scaffoldState
        ) {
            // Screen content
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {


                Text(
                    color = white, modifier = Modifier
                        .fillMaxWidth()
                        .background(black),
                    text = "Made by: ${stringResource(id = R.string.author_name)}, ${
                        stringResource(
                            id = R.string.author_email
                        )
                    }"
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    MainContent()
                }
            }
        }
    }

    private fun openCustomPreferencesActivity() {
        val intent = Intent(this, CustomPreferencesActivity::class.java)
        startActivity(intent)
    }

    private fun openMainWindowActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun openHelpActivity() {
        val intent = Intent(this, HelpActivity::class.java)
        startActivity(intent)
    }

    private fun openLocalBroadcastActivity() {
        val intent = Intent(this, LocalBroadcastActivity::class.java)
        startActivity(intent)
    }

    private fun openBackgroundTaskActivity() {
        val intent = Intent(this, BackgroundTaskActivity::class.java)
        startActivity(intent)
    }

    @Composable
    abstract fun MainContent()


}