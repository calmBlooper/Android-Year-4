package com.example.levchukapplication.views


import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.levchukapplication.data.Word
import com.example.levchukapplication.data.WordRepository
import com.example.levchukapplication.data.WordRoomDatabase
import com.example.levchukapplication.storage.Preferences
import com.example.levchukapplication.style.*
import com.example.levchukapplication.viewmodel.WordViewModel
import com.example.levchukapplication.viewmodel.WordViewModelFactory
import com.pes.androidmaterialcolorpickerdialog.ColorPicker


class CustomPreferencesActivity : BaseActivity() {

    override val windowName: String = "Custom preferences window"
    private val backgroundColor = mutableStateOf(white)
    private val fontColor = mutableStateOf(black)
    private val sharedPreferencesCustomStringsMap = mutableStateMapOf<String, String>()
    private val dataBaseCustomWordsList = mutableStateListOf<Word>()
    private val currentPendingKey = mutableStateOf("")
    private val currentPendingValue = mutableStateOf("")
    val database by lazy { WordRoomDatabase.getDatabase(this) }
    val repository by lazy { WordRepository(database.wordDao()) }
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((repository))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshSharedPreferencesData()
        wordViewModel.allWords.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words?.let {
                dataBaseCustomWordsList.clear()
                dataBaseCustomWordsList.addAll(it)
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview
    @Composable
    override fun MainContent() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(pinkText)
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ColorPickerButton(
                    color = backgroundColor.value,
                    text = "Change background color",
                    action = { pickedColor ->
                        backgroundColor.value = pickedColor
                        Preferences(this@CustomPreferencesActivity).saveDbBackgroundColor(
                            pickedColor
                        )
                    })
                ColorPickerButton(
                    color = fontColor.value,
                    text = "Change font color",
                    action = { pickedColor ->
                        fontColor.value = pickedColor
                        Preferences(this@CustomPreferencesActivity).saveDbFontColor(pickedColor)
                    })
            }
            Text(
                "Data from Shared Preferences", modifier = Modifier
                    .wrapContentHeight()
                    .padding(bottom = 4.dp, top = 16.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 16.dp)
                    .background(backgroundColor.value)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(30.dp)
                        .background(blueBG),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Key", Modifier.weight(1f))
                    Text(text = "Value", Modifier.weight(1f))
                    Spacer(modifier = Modifier.weight(1f))
                }


                if (sharedPreferencesCustomStringsMap.isEmpty()) {

                    Text(
                        "No string found!",
                        color = fontColor.value,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
                sharedPreferencesCustomStringsMap.forEach {
                    SharedPreferencesItem(
                        key = it.key,
                        value = it.value
                    )
                }
            }
            Text("Add string", modifier = Modifier.wrapContentSize())
            AddNewCustomStringView()
            Text(
                "Data from the database", modifier = Modifier
                    .wrapContentHeight()
                    .padding(bottom = 4.dp, top = 16.dp)
            )
            Button(onClick = {
                wordViewModel.deleteAll()
                sharedPreferencesCustomStringsMap.forEach {
                    wordViewModel.insert(Word(it.key, it.value))
                }
            }) {
                Text("Sync with shared preferences")
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 16.dp)
                    .background(backgroundColor.value)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(30.dp)
                        .background(blueBG),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Key", Modifier.weight(1f))
                    Text(text = "Value", Modifier.weight(1f))
                    Spacer(modifier = Modifier.weight(1f))
                }


                if (dataBaseCustomWordsList.isEmpty()) {

                    Text(
                        "No string found!",
                        color = fontColor.value,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
                dataBaseCustomWordsList.forEach {
                    DataBaseItem(key = it.prefKey, value = it.prefValue)
                }
            }

        }
    }

    private fun refreshSharedPreferencesData() {
        val sharedPreferences = Preferences(this)
        backgroundColor.value = sharedPreferences.getDbBackgroundColor(white)
        fontColor.value = sharedPreferences.getDbFontColor(black)
        sharedPreferencesCustomStringsMap.clear()
        sharedPreferencesCustomStringsMap.putAll(sharedPreferences.getSavedCustomStringsMap())
    }

    @Preview
    @Composable
    private fun AddNewCustomStringView() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val canSave =
                currentPendingKey.value.isNotBlank() && currentPendingValue.value.isNotBlank()
            TextField(value = currentPendingKey.value, onValueChange = {
                currentPendingKey.value = it
            }, label = { Text("Key") }, modifier = Modifier
                .padding(8.dp)
                .weight(1f)
            )
            TextField(value = currentPendingValue.value, onValueChange = {
                currentPendingValue.value = it
            }, label = { Text("Value") }, modifier = Modifier
                .padding(8.dp)
                .weight(1f)
            )
            if (canSave) {

                Button(
                    onClick = {
                        Preferences(this@CustomPreferencesActivity).saveCustomString(
                            currentPendingKey.value,
                            currentPendingValue.value
                        )
                        currentPendingKey.value = ""
                        currentPendingValue.value = ""
                        refreshSharedPreferencesData()
                    }, modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Text("Save to shared preferences")
                }
            }
        }
    }

    @Composable
    private fun SharedPreferencesItem(key: String, value: String) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = key, color = fontColor.value)
            Text(text = value, color = fontColor.value, modifier = Modifier)
            Button(onClick = {
                Preferences(this@CustomPreferencesActivity).deleteSavedCustomString(key)
                refreshSharedPreferencesData()
            }, colors = ButtonDefaults.buttonColors(redSos)) {
                Text("Delete")
            }
        }
    }


    @Composable
    private fun DataBaseItem(key: String, value: String) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = key, color = fontColor.value)
            Text(text = value, color = fontColor.value, modifier = Modifier)
            Spacer(Modifier.width(40.dp))
        }
    }

    private fun openColorPickerDialog(currentColor: Color, action: (Color) -> Unit) {
        val cp = ColorPicker(
            this,
            (currentColor.alpha * 255).toInt(),
            (currentColor.red * 255).toInt(),
            (currentColor.green * 255).toInt(),
            (currentColor.blue * 255).toInt()
        )
        cp.show()

        cp.enableAutoClose()
        cp.setCallback { color ->
            action(Color(color))
        }
    }

    @Composable
    private fun ColorPickerButton(color: Color, text: String, action: (Color) -> Unit) {
        Row(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
                .border(2.dp, mainVioletAccent, RoundedCornerShape(20.dp))
                .clickable {
                    openColorPickerDialog(color, action)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .background(shape = CircleShape, color = color)
                    .size(30.dp)

            )
            Divider(Modifier.width(4.dp))
            Text(text = text, overflow = TextOverflow.Ellipsis)
        }
    }
}