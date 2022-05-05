package com.example.levchukapplication.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.levchukapplication.style.redSos
import com.example.levchukapplication.style.white

abstract class BaseFragment : Fragment() {

    abstract val fragmentName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            setContent {
                ContentWrap()
            }
        }
    }

    @Composable
    private fun ContentWrap() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(redSos),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { backAction() }, modifier = Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = white,
                        contentDescription = "Back action"
                    )
                }
                Text(
                    text = fragmentName,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Visible,
                    modifier = Modifier.weight(1f),
                    color = white
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            MainContent()
        }
    }

    private fun backAction() {
        activity?.onBackPressed()
    }

    @Composable
    abstract fun MainContent()

}