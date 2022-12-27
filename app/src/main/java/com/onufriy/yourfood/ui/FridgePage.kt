package com.onufriy.yourfood.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.onufriy.yourfood.http.YourFoodApiImpl
import com.onufriy.yourfood.request.UpdateFridge
import com.onufriy.yourfood.request.UserInfo
import com.onufriy.yourfood.response.FridgeResponse
import com.onufriy.yourfood.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun FridgePage(nav: NavController, selectId: String, login: String){
    val focusManager = LocalFocusManager.current
    var address = remember {
        mutableStateOf("")
    }

    val client = YourFoodAPI.create()
    val Fridge = remember {
        mutableStateOf(emptyList<FridgeResponse>())
    }

    produceState<List<FridgeResponse>>(
        initialValue = emptyList(),
        producer = {
            if(address.value.length==0){
                value = client.getAllFridge()
            }else{
                value = client.getFridgeByAddress(address.value)
            }
            Fridge.value = value

        })

    val coroutineScope = rememberCoroutineScope()
    fun updateList(){

        coroutineScope.launch{
            if(address.value.length==0){
                Fridge.value = client.getAllFridge()
            }else{
                Fridge.value = client.getFridgeByAddress(address.value)
            }
        }
    }

    Column( modifier = Modifier
        .fillMaxSize()
        .background(color = Red100),) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(0.92f)) {
            IconButton(onClick = {  nav.navigate("profile") } ) {
                Icon(imageVector = Icons.Filled.ArrowBack
                    ,"Back"
                    , modifier = Modifier.size(50.dp))
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = "Change fridge",
                style = MaterialTheme.typography.h3
            )

        }
        Text(
            text = "Address",
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(start = 60.dp, top = 30.dp)
        )
        TextField(

            shape = RoundedCornerShape(60.dp),
            value = address.value,
            onValueChange = {
                address.value = it
                updateList()
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }),
            modifier = Modifier
                .size(300.dp, 50.dp)
                .border(
                    width = 3.dp,
                    color = Color.Black,
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
        LazyColumn(
            modifier = Modifier.padding(top = 10.dp)
        ) {
            itemsIndexed(Fridge.value) {index, item ->
                FridgeCard(nav,client, login, address = item.adress, countCell = item.cellCount, countUser = item.userCount, folow = item.fridgeId == selectId.toInt(), item.fridgeId)
                if(index+1 == Fridge.value.size){
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }

        }

    }
}

@Composable
fun FridgeCard(nav: NavController,client: YourFoodAPI, login: String, address: String, countCell: Int, countUser: Int, folow: Boolean, id: Int){

    val coroutineScope = rememberCoroutineScope()
    fun updateFridge(id1: Int){

        coroutineScope.launch{
            val UpdateFridge = UpdateFridge(id1,login)
            val response = client.updateFridge(UpdateFridge)
            if(response.value == 200){
                nav.navigate("profile")
            }

        }
    }

    var status = "Select this";
    var ColorButton = Green300
    var enable = true
    var yourfridge = false
    if(folow){
        status = "Unselect"
        ColorButton = Red250
        yourfridge = true
    }else if (countUser >= countCell){
        status = "Fridge is full"
        ColorButton = Red300
        enable = false
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        backgroundColor = Red300,
        shape = RoundedCornerShape(25.dp),
    ) {
        Box(modifier = Modifier
            .size(0.dp, 170.dp)
            .fillMaxWidth(),){
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = address,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(start = 30.dp, bottom = 10.dp))
                    Text(text = "Full at " + countUser.toString() + "/" + countCell.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(start = 20.dp, bottom = 10.dp))
                    Button(
                        enabled = enable,
                        onClick = {
                            if(yourfridge){
                                updateFridge(0)
                            }else{
                                updateFridge(id)
                            }

                        },
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .fillMaxWidth(0.7f)
                            .height(80.dp)
                            .padding(top = 10.dp, bottom = 10.dp)
                            .border(2.dp, Color.Black, RoundedCornerShape(60.dp)),
                        shape = RoundedCornerShape(60.dp),

                        colors = ButtonDefaults.buttonColors(backgroundColor = ColorButton, disabledBackgroundColor = ColorButton, disabledContentColor = Color.Black),
                    ) {
                        Text(text = status, style = MaterialTheme.typography.h5,)
                    }
                }
        }
    }
}