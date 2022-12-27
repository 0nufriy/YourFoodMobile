package com.onufriy.yourfood.ui

import android.os.Message
import android.text.TextUtils
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.onufriy.yourfood.http.YourFoodAPI
import com.onufriy.yourfood.request.RegisterRequest
import com.onufriy.yourfood.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun RegisterPage(nav: NavController){
    val focusManager = LocalFocusManager.current
    var login = remember {
        mutableStateOf("")
    }
    var password = remember {
        mutableStateOf("")
    }
    var repassword = remember {
        mutableStateOf("")
    }
    var name = remember {
        mutableStateOf("")
    }
    var email = remember {
        mutableStateOf("")
    }
    var phone = remember {
        mutableStateOf("")
    }
    var colorTextFieldBorderPassword = remember {
        mutableStateOf(Color.Black)
    }
    var widhtTextFieldBorderPassword = remember {
        mutableStateOf(3.dp)
    }

    var colorTextFieldBorderLogin = remember {
        mutableStateOf(Color.Black)
    }
    var widhtTextFieldBorderLogin = remember {
        mutableStateOf(3.dp)
    }
    var colorTextFieldBorderName = remember {
        mutableStateOf(Color.Black)
    }
    var widhtTextFieldBorderName = remember {
        mutableStateOf(3.dp)
    }
    var colorTextFieldBorderEmail = remember {
        mutableStateOf(Color.Black)
    }
    var widhtTextFieldBorderEmail = remember {
        mutableStateOf(3.dp)
    }
    var colorTextFieldBorderPhone = remember {
        mutableStateOf(Color.Black)
    }
    var widhtTextFieldBorderPhone = remember {
        mutableStateOf(3.dp)
    }
    val client = YourFoodAPI.create()

    val coroutineScope = rememberCoroutineScope()

    fun Register(){

        if(login.value.length < 3 || login.value.length > 16){
            widhtTextFieldBorderLogin.value = 5.dp
            colorTextFieldBorderLogin.value = Color.Red
            widhtTextFieldBorderPassword.value = 3.dp
            colorTextFieldBorderPassword.value = Color.Black
            widhtTextFieldBorderName.value = 3.dp
            colorTextFieldBorderName.value = Color.Black
            widhtTextFieldBorderEmail.value = 3.dp
            colorTextFieldBorderEmail.value = Color.Black
            widhtTextFieldBorderPhone.value = 3.dp
            colorTextFieldBorderPhone.value = Color.Black
            return
        }
        if(password.value.length < 3 || password.value.length > 16 || password.value != repassword.value){
            widhtTextFieldBorderPassword.value = 5.dp
            colorTextFieldBorderPassword.value = Color.Red
            widhtTextFieldBorderLogin.value = 3.dp
            colorTextFieldBorderLogin.value = Color.Black
            widhtTextFieldBorderName.value = 3.dp
            colorTextFieldBorderName.value = Color.Black
            widhtTextFieldBorderEmail.value = 3.dp
            colorTextFieldBorderEmail.value = Color.Black
            widhtTextFieldBorderPhone.value = 3.dp
            colorTextFieldBorderPhone.value = Color.Black
            return
        }
        if(name.value.length < 3 || name.value.length > 16){
            widhtTextFieldBorderName.value = 5.dp
            colorTextFieldBorderName.value = Color.Red
            widhtTextFieldBorderPassword.value = 3.dp
            colorTextFieldBorderPassword.value = Color.Black
            widhtTextFieldBorderLogin.value = 3.dp
            colorTextFieldBorderLogin.value = Color.Black
            widhtTextFieldBorderEmail.value = 3.dp
            colorTextFieldBorderEmail.value = Color.Black
            widhtTextFieldBorderPhone.value = 3.dp
            colorTextFieldBorderPhone.value = Color.Black
            return
        }
        if(email.value.length < 3 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()){
            widhtTextFieldBorderEmail.value = 5.dp
            colorTextFieldBorderEmail.value = Color.Red
            widhtTextFieldBorderLogin.value = 3.dp
            colorTextFieldBorderLogin.value = Color.Black
            widhtTextFieldBorderPassword.value = 3.dp
            colorTextFieldBorderPassword.value = Color.Black
            widhtTextFieldBorderName.value = 3.dp
            colorTextFieldBorderName.value = Color.Black
            widhtTextFieldBorderPhone.value = 3.dp
            colorTextFieldBorderPhone.value = Color.Black
            return
        }
        if(phone.value.length < 3 || !android.util.Patterns.PHONE.matcher(phone.value).matches()){
            widhtTextFieldBorderPhone.value = 5.dp
            colorTextFieldBorderPhone.value = Color.Red
            widhtTextFieldBorderLogin.value = 3.dp
            colorTextFieldBorderLogin.value = Color.Black
            widhtTextFieldBorderPassword.value = 3.dp
            colorTextFieldBorderPassword.value = Color.Black
            widhtTextFieldBorderName.value = 3.dp
            colorTextFieldBorderName.value = Color.Black
            widhtTextFieldBorderEmail.value = 3.dp
            colorTextFieldBorderEmail.value = Color.Black
            return
        }
        val RegisterRequest = RegisterRequest(login.value,name.value,password.value,email.value,phone.value,"User")
        coroutineScope.launch {

            val reg = client.register(request = RegisterRequest)
            if (reg.value == 200){
                nav.navigate("main/" + login.value)
            }else{
                widhtTextFieldBorderPhone.value = 5.dp
                colorTextFieldBorderPhone.value = Color.Red
                widhtTextFieldBorderEmail.value = 5.dp
                colorTextFieldBorderEmail.value = Color.Red
                widhtTextFieldBorderLogin.value = 5.dp
                colorTextFieldBorderLogin.value = Color.Red
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
                .fillMaxHeight(0.99f),
            backgroundColor = Red200,
            shape = RoundedCornerShape(50.dp),
        ) {
            Column(
                modifier = Modifier,
            ) {
                Text(
                    text = "Login:",
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
                            width = widhtTextFieldBorderLogin.value,
                            color = colorTextFieldBorderLogin.value,
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
                    text = "Password:",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(horizontal = 40.dp)
                )
                TextField(

                    shape = RoundedCornerShape(60.dp),
                    value = repassword.value,
                    onValueChange = {
                        repassword.value = it
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    modifier = Modifier
                        .size(300.dp, 50.dp)
                        .border(
                            width = widhtTextFieldBorderPassword.value,
                            color = colorTextFieldBorderPassword.value,
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
                Text(
                    text = "Re-enter the password:",
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
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    modifier = Modifier
                        .size(300.dp, 50.dp)
                        .border(
                            width = widhtTextFieldBorderPassword.value,
                            color = colorTextFieldBorderPassword.value,
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
                Text(
                    text = "Name:",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(start = 40.dp)
                )
                TextField(

                    shape = RoundedCornerShape(60.dp),
                    value = name.value,
                    onValueChange = {
                        name.value = it
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    modifier = Modifier
                        .size(300.dp, 50.dp)
                        .border(
                            width = widhtTextFieldBorderName.value,
                            color = colorTextFieldBorderName.value,
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
                    text = "Email:",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(start = 40.dp)
                )
                TextField(

                    shape = RoundedCornerShape(60.dp),
                    value = email.value,
                    onValueChange = {
                        email.value = it
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    modifier = Modifier
                        .size(300.dp, 50.dp)
                        .border(
                            width = widhtTextFieldBorderEmail.value,
                            color = colorTextFieldBorderEmail.value,
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
                    text = "Phone:",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(start = 40.dp)
                )
                TextField(

                    shape = RoundedCornerShape(60.dp),
                    value = phone.value,
                    onValueChange = {
                        phone.value = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            Register()
                        }),
                    modifier = Modifier
                        .size(300.dp, 50.dp)
                        .border(
                            width = widhtTextFieldBorderPhone.value,
                            color = colorTextFieldBorderPhone.value,
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
                Button(
                    onClick = { Register()},
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .fillMaxWidth(0.7f).height(90.dp)
                        .padding(top = 30.dp, bottom = 10.dp)
                        .border(2.dp,Color.Black, shape = RoundedCornerShape(60.dp)),
                    shape = RoundedCornerShape(60.dp),
                    colors = ButtonDefaults.buttonColors(Red300),
                ) {
                    Text(text = "Sing Up", style = MaterialTheme.typography.h5,)
                }
                Button(
                    onClick = { nav.navigate("login")},
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .fillMaxWidth(0.4f).height(70.dp)
                        .padding(top = 10.dp, bottom = 10.dp)
                        .border(2.dp,Color.Black, shape = RoundedCornerShape(60.dp)),
                    shape = RoundedCornerShape(60.dp),
                    colors = ButtonDefaults.buttonColors(Red250),
                ) {
                    Text(text = "Sign In", style = MaterialTheme.typography.h5,)
                }
                Text(
                    text = "By registering you accept the terms of use of the YourFood application software",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(horizontal = 20.dp),

                )
            }

        }
    }

}