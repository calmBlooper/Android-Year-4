package com.example.levchukapplication.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.levchukapplication.R
import com.example.levchukapplication.style.panicManualGroupBackground

class HelpActivity : BaseActivity() {

    override val windowName: String = "Help window"

    @Preview
    @Composable
    override fun MainContent() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(panicManualGroupBackground)
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Completed tasks:",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight()
                    .padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.weight(1f),text="Програма здана до дедлайну", overflow = TextOverflow.Ellipsis)
                Checkbox(checked = true, onCheckedChange = {})
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(modifier = Modifier.weight(1f),text="Наявність меню NavigationBar", overflow = TextOverflow.Ellipsis)
                Checkbox(checked = true, onCheckedChange = {})
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier.weight(1f),text="Генерація бродкаст повідомлення(Local broadcast window)",
                    overflow = TextOverflow.Ellipsis
                )
                Checkbox(checked = true, onCheckedChange = {})
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier.weight(1f),text="Запуск фонового завдання(Background task window)",
                    overflow = TextOverflow.Ellipsis
                )
                Checkbox(checked = true, onCheckedChange = {})
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text( modifier = Modifier.weight(1f),text="Наявність вікна опису функціоналу (Help): ", overflow = TextOverflow.Ellipsis)
                Checkbox(checked = true, onCheckedChange = {})
            }

        }
    }
}