package com.onufriy.yourfood.ui

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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.onufriy.yourfood.http.YourFoodAPI
import com.onufriy.yourfood.request.LoginRequest
import com.onufriy.yourfood.request.UserInfo
import com.onufriy.yourfood.ui.theme.Greay300
import com.onufriy.yourfood.ui.theme.Red100
import com.onufriy.yourfood.ui.theme.Red200
import com.onufriy.yourfood.ui.theme.Red300
import kotlinx.coroutines.launch
import kotlin.math.log


@Composable
fun ProfilePage(nav: NavController, navun: NavController, login: String){

    val client = YourFoodAPI.create()
    var temperature = remember {
        mutableStateOf("")
}
    var textButtonChange = remember {
        mutableStateOf("Select Fridge")
    }
    var addressText = remember {
        mutableStateOf("")
    }
    val User = produceState<UserInfo>(
        initialValue = UserInfo("Error","Error","Error","Error","Error","Error",1,"Error", 0.0),
        producer = {
            value = client.getUserInfo(login)
            if(value.temperature != -300.0){
                temperature.value = value.temperature.toString() +"°С";
                textButtonChange.value = "Change Fridge"
                addressText.value = "Adress: " + value.adress
            }
        })




    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Red100),
    ){
        Column(modifier = Modifier
            .fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(modifier = Modifier.width(50.dp))
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = login.lowercase(),
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    navun.navigate("login")
                } ) {
                    Icon(imageVector = Icons.Filled.ExitToApp
                        ,"Back"
                        , modifier = Modifier.size(50.dp))
                }

            }

            Text(
                text = "Name: " + User.value.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 10.dp, top = 15.dp)
            )
            Text(
                text = "Email: " + User.value.email,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 10.dp, top = 15.dp)
            )
            Text(
                text = "Phone: " + User.value.phone,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 10.dp, top = 15.dp)
            )
            Text(
                text = "Pass: " + User.value.passName,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 10.dp, top = 15.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 20.dp),
                backgroundColor = Red300,
                shape = RoundedCornerShape(25.dp)
            ) {
                Box(modifier = Modifier
                    .size(0.dp, 210.dp)
                    .fillMaxWidth(),) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Fridge",style = MaterialTheme.typography.h4, modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .padding(start = 20.dp))
                        Text(text = addressText.value, maxLines = 2, style = MaterialTheme.typography.h5,
                            modifier = Modifier
                                .align(alignment = Alignment.Start)
                                .height(60.dp)
                                .padding(start = 20.dp, end = 20.dp))
                        Text(temperature.value,style = MaterialTheme.typography.h5, textAlign = TextAlign.Center)
                        Button(
                            onClick = { nav.navigate("chnageFridge/" + User.value.fridgeId.toString())},
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .padding(top = 10.dp)
                                .height(50.dp)
                                .border(2.dp, Color.Black, RoundedCornerShape(60.dp)),
                            shape = RoundedCornerShape(60.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                        ) {
                            Text(text = textButtonChange.value, style = MaterialTheme.typography.h5)
                        }
                    }

                }
            }

        }
    }
}