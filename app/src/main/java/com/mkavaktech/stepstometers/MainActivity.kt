package com.mkavaktech.stepstometers

import CustomTextField
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.Female
import androidx.compose.material.icons.rounded.Male
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mkavaktech.stepstometers.ui.theme.StepsToMetersTheme


@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    StepsToMetersTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                AppBar()
            }
        ) {
            content()
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Steps to Meters",
                )
            }
        },
    )
}

@ExperimentalComposeUiApi
@Composable
fun MainContent(modifier: Modifier = Modifier) {
    val strideLengthState = remember {
        mutableStateOf(0)
    }
    val metersState = remember {
        mutableStateOf(0)
    }
    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    val heightVal = (sliderPositionState.value).toInt()
    val validStateOfHeight = remember(heightVal) {
        mutableStateOf(heightVal > 1)
    }
    val activeTabIndexState = remember {
        mutableStateOf(value = 0)
    }
    val stepsState = remember {
        mutableStateOf(value = "")
    }
    val validState = remember(stepsState.value) {
        mutableStateOf(stepsState.value.trim().isNotEmpty())
    }

    Column(modifier = modifier.padding(12.dp)) {
        StepsForm(
            metersState = metersState,
            strideLengthState = strideLengthState,
            activeTabIndexState = activeTabIndexState,
            sliderPositionState = sliderPositionState,
            validStateOfHeight = validStateOfHeight,
            validState = validState,
            stepsState = stepsState,
            heightVal = heightVal
        )
        Spacer(modifier = modifier.size(12.dp))
        BottomInfoCard(modifier, metersState)
    }
}

@Composable
fun BottomInfoCard(
    modifier: Modifier,
    metersState: MutableState<Int>
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(144.dp)
            .clip(shape = RoundedCornerShape(size = 24.dp)),
        color = Color(color = 0xFF3F51B5)
    ) {
        Column(
            modifier = modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Steps to Meters:",
                style = MaterialTheme.typography.h5.copy(color = Color.White)
            )
            Text(
                text = "${metersState.value / 100} meters",
                style = MaterialTheme.typography.h4.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}


@ExperimentalComposeUiApi
@Composable
fun StepsForm(
    modifier: Modifier = Modifier,
    metersState: MutableState<Int>,
    strideLengthState: MutableState<Int>,
    activeTabIndexState: MutableState<Int>,
    sliderPositionState: MutableState<Float>,
    stepsState: MutableState<String>,
    validStateOfHeight: MutableState<Boolean>,
    validState: MutableState<Boolean>,
    heightVal: Int,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    onValueChange: (String) -> Unit = {},
) {
    val genderIconList = listOf(Icons.Rounded.Male, Icons.Rounded.Female)
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp),
        shape = RoundedCornerShape(size = 8.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column {
            StepsTextField(stepsState, validState, onValueChange, keyboardController)
            Divider()
            Row(
                modifier = modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Gender")
                GenderTabRow(
                    modifier,
                    activeTabIndexState,
                    genderIconList,
                    validState,
                    strideLengthState,
                    heightVal,
                    metersState,
                    stepsState
                )
            }
            Divider()
            Row(
                modifier = modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Height")
                HeightSlider(
                    modifier,
                    sliderPositionState,
                    validState,
                    strideLengthState,
                    heightVal,
                    activeTabIndexState,
                    metersState,
                    stepsState
                )
            }
            Text(text = "Your Height: $heightVal cm", modifier = modifier.padding(12.dp))
            if (validStateOfHeight.value) {
                Text(
                    text = "Stride Length: ${
                        strideLengthState.value
                    } cm",
                    modifier = modifier.padding(12.dp)
                )
            } else {
                Box {
                }
            }
        }
    }
}

@Composable
@ExperimentalComposeUiApi
fun StepsTextField(
    stepsState: MutableState<String>,
    validState: MutableState<Boolean>,
    onValueChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    CustomTextField(
        valueState = stepsState,
        labelText = "Enter Steps",
        leadingIcons = Icons.Rounded.DirectionsRun,
        onActions = KeyboardActions {
            if (!validState.value) return@KeyboardActions
            onValueChange(stepsState.value.trim())
            keyboardController?.hide()
        }
    )
}

@Composable
fun HeightSlider(
    modifier: Modifier,
    sliderPositionState: MutableState<Float>,
    validState: MutableState<Boolean>,
    strideLengthState: MutableState<Int>,
    heightVal: Int,
    activeTabIndexState: MutableState<Int>,
    metersState: MutableState<Int>,
    stepsState: MutableState<String>
) {
    Slider(
        modifier = modifier.padding(12.dp),
        value = sliderPositionState.value,
        valueRange = 0f..250f,
        onValueChange = { newValue ->
            sliderPositionState.value = newValue
            if (validState.value) {
                strideLengthState.value =
                    calculateStrideLength(
                        heightVal = heightVal,
                        genderVal = activeTabIndexState.value
                    )
                metersState.value = calculateStepsToMeters(
                    strideHeightValue = strideLengthState.value,
                    stepsValue = stepsState.value.toInt()
                )
            }


        },
    )
}

@Composable
fun GenderTabRow(
    modifier: Modifier,
    activeTabIndexState: MutableState<Int>,
    genderIconList: List<ImageVector>,
    validState: MutableState<Boolean>,
    strideLengthState: MutableState<Int>,
    heightVal: Int,
    metersState: MutableState<Int>,
    stepsState: MutableState<String>
) {
    TabRow(
        modifier = modifier.padding(12.dp),
        selectedTabIndex = activeTabIndexState.value,
        indicator = { tabPositions ->
            Box(
                modifier = modifier
                    .tabIndicatorOffset(tabPositions[activeTabIndexState.value])
                    .fillMaxSize()
                    .background(color = Color.Red)
                    .zIndex(zIndex = -1F)
            )
        }
    ) {
        genderIconList.forEachIndexed { index, icon ->
            Tab(
                selected = activeTabIndexState.value == index,
                onClick = {
                    activeTabIndexState.value = index
                    if (validState.value) {
                        strideLengthState.value =
                            calculateStrideLength(
                                heightVal = heightVal,
                                genderVal = activeTabIndexState.value
                            )
                        metersState.value = calculateStepsToMeters(
                            strideHeightValue = strideLengthState.value,
                            stepsValue = stepsState.value.toInt()
                        )
                    }

                },
                icon = {
                    Icon(imageVector = icon, contentDescription = "genderIcon")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@ExperimentalComposeUiApi
@Composable
fun DefaultPreview() {
    StepsToMetersTheme {
        MyApp {
            MainContent()
        }
    }
}