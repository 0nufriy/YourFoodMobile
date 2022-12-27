package com.onufriy.yourfood

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.onufriy.yourfood.http.YourFoodAPI
import com.onufriy.yourfood.request.LoginRequest
import com.onufriy.yourfood.ui.theme.Greay300
import com.onufriy.yourfood.ui.theme.Red100
import com.onufriy.yourfood.ui.theme.Red200
import com.onufriy.yourfood.ui.theme.Red300
import io.ktor.http.*
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.launch


@Composable
fun LoginPage(nav: NavController){


    val focusManager = LocalFocusManager.current
    var login = remember {
        mutableStateOf("")
    }
    var password = remember {
        mutableStateOf("")
    }
    var colorTextFieldBorder = remember {
        mutableStateOf(Color.Black)
    }
    var widhtTextFieldBorder = remember {
        mutableStateOf(3.dp)
    }
    val client = YourFoodAPI.create()

    val coroutineScope = rememberCoroutineScope()


    fun login(){
        val LoginRequest = LoginRequest(login.value, password.value, false)
        coroutineScope.launch {

            val login1 = client.login(request = LoginRequest)
            if (login1.value == 200){
                nav.navigate("main/" + login.value)
            }else{
                widhtTextFieldBorder.value = 5.dp
                colorTextFieldBorder.value = Color.Red
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Red100),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.75f),
            backgroundColor = Red200,
            shape = RoundedCornerShape(50.dp),
        ) {
            Column(
                modifier = Modifier,
            ) {
                Text(
                    text = "YourFood",
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
                Text(
                    text = "Sign In",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(start = 40.dp, top = 30.dp)
                )
                TextField(

                    shape = RoundedCornerShape(60.dp),
                    value = login.value,
                    onValueChange = {
                        login.value = it
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    modifier = Modifier
                        .size(300.dp, 50.dp)
                        .border(
                            width = widhtTextFieldBorder.value,
                            color = colorTextFieldBorder.value,
                            shape = RoundedCornerShape(60.dp),
                        )
                        .align(alignment = Alignment.CenterHorizontally)
                        .background(color = Greay300, shape = RoundedCornerShape(60.dp)),
                    colors = TextFieldDefaults.textFieldColors( focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black),
                    textStyle = TextStyle(fontSize = 10.sp),

                )
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(horizontal = 40.dp)
                )
                TextField(

                    shape = RoundedCornerShape(60.dp),
                    value = password.value,
                    onValueChange = {
                        password.value = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            login()
                        }),
                    modifier = Modifier
                        .size(300.dp, 50.dp)
                        .border(
                            width = widhtTextFieldBorder.value,
                            color = colorTextFieldBorder.value,
                            shape = RoundedCornerShape(60.dp),
                        )
                        .align(alignment = Alignment.CenterHorizontally)
                        .background(color = Greay300, shape = RoundedCornerShape(60.dp)),
                    colors = TextFieldDefaults.textFieldColors( focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black),
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = TextStyle(fontSize = 10.sp),

                )
                Button(

                    onClick = {
                        login()
                     },
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .fillMaxWidth(0.7f)
                        .height(110.dp)
                        .padding(top = 30.dp, bottom = 30.dp)
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(60.dp)),
                    shape = RoundedCornerShape(60.dp),
                    colors = ButtonDefaults.buttonColors(Red300),
                ) {
                    Text(text = "Sing In", style = MaterialTheme.typography.h5,)
                }
                Text(
                    text = "No account?",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
                Button(
                    onClick = { nav.navigate("register") },
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .fillMaxWidth(0.7f)
                        .height(110.dp)
                        .padding(top = 30.dp, bottom = 30.dp)
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(60.dp)),
                    shape = RoundedCornerShape(60.dp),
                    colors = ButtonDefaults.buttonColors(Red300),
                ) {
                    Text(text = "Sing Up", style = MaterialTheme.typography.h5,)
                }
            }

        }
    }

}
