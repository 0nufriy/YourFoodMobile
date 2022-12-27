package com.onufriy.yourfood.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.onufriy.yourfood.request.UpdateFridge
import com.onufriy.yourfood.request.UpdateOrderStatus
import com.onufriy.yourfood.request.UserInfo
import com.onufriy.yourfood.response.FridgeResponse
import com.onufriy.yourfood.response.OrdersResponse
import com.onufriy.yourfood.ui.theme.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


@Composable
fun OrderPage(nav:NavController,login: String){
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val current = formatter.format(time).toString()

    val client = YourFoodAPI.create()

    val OrdersList = remember {
        mutableStateOf(mutableListOf<OrdersResponse>())
    }

    produceState<MutableList<OrdersResponse>>(
        initialValue = mutableListOf<OrdersResponse>(),
        producer = {
            OrdersList.value = client.getUserOrders(login)
            if(OrdersList.value.size == 0 || OrdersList.value[0].date.substring(0,10) != current){
                var a = client.getUserInfo(login)
                if(a.passName!= "" && a.adress != ""){
                    var newOrders =  mutableListOf<OrdersResponse>()
                    newOrders.add(0,OrdersResponse(0,login,1,a.passName,a.adress,"хуй",current.toString().replace('-','/'),0))

                    for (orders in OrdersList.value ){
                        newOrders.add(orders)
                    }
                    OrdersList.value = newOrders
                }
            }

        })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Red100),
    ){
        Column(modifier = Modifier
            .fillMaxSize()){
            Text(
                text = "Your Orders",
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )

            LazyColumn{
                itemsIndexed(OrdersList.value){index,item ->
                    OrderCard(client, nav, item.orderID, passName = item.passName, data = item.date.substring(0,10), status = item.status.trim(), cellId = item.orderID, item.adress)
                    if(index+1 == OrdersList.value.size){
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }

            }

        }
    }
}

@Composable
fun OrderCard(client: YourFoodAPI, nav: NavController,orderId: Int, passName: String, data: String, status: String, cellId: Int, address: String){
    var statusText = "The order has not been formed yet"
    var ColorButton = Color.White
    if(status == "In road"){
        statusText = "The order in road"
        ColorButton = Orange300
    }else  if(status == "In cell"){
        statusText = "The order in cell No." + cellId.toString()
        ColorButton = Yellow300
    }else if(status == "Done"){
        statusText = "The order done"
        ColorButton = Green300
    }else if(status == "Declined"){
        statusText = "The order declined"
        ColorButton = Red250
    }

    val coroutineScope = rememberCoroutineScope()
    fun updateStatus(){

        coroutineScope.launch{

            val UpdateOrderStatus = UpdateOrderStatus(orderId,"Done")
            val response = client.updateOrderStatus(UpdateOrderStatus)
            if(response.value == 200){
                nav.navigate("orders")
            }

        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        backgroundColor = Red300,
        shape = RoundedCornerShape(25.dp),
    ){
        Box(modifier = Modifier
            .size(0.dp, 260.dp)
            .fillMaxWidth(),){
            Column( modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {

                    Text(text = passName, style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(start = 10.dp))
                    Spacer(Modifier.weight(1f))
                    Text(text = data, style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(top = 3.dp, end = 5.dp))
                }
                Text(text = address,  maxLines = 2, style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(top = 3.dp, end = 10.dp, start = 10.dp).height(60.dp))
                if(status != "In cell"){
                    Button(
                        enabled = false,
                        onClick = {},
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .fillMaxWidth(0.7f)
                            .height(110.dp)
                            .padding(top = 10.dp, bottom = 10.dp)
                            .border(2.dp, Color.Black, RoundedCornerShape(60.dp)),
                        shape = RoundedCornerShape(60.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = ColorButton, disabledBackgroundColor = ColorButton, disabledContentColor = Color.Black),
                    ) {

                        Text(text = statusText, style = MaterialTheme.typography.h5,  textAlign = TextAlign.Center)
                    }
                }else{
                    Button(
                        enabled = false,
                        onClick = {},
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .fillMaxWidth(0.8f)
                            .height(60.dp)
                            .padding(top = 5.dp, bottom = 5.dp)
                            .border(2.dp, Color.Black, RoundedCornerShape(60.dp)),
                        shape = RoundedCornerShape(60.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = ColorButton, disabledBackgroundColor = ColorButton, disabledContentColor = Color.Black),
                    ) {

                        Text(text = statusText, style = MaterialTheme.typography.h5,  textAlign = TextAlign.Center)
                    }
                    Button(
                        enabled = true,
                        onClick = {
                            updateStatus()
                        },
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .fillMaxWidth(0.4f)
                            .height(50.dp)
                            .border(2.dp, Color.Black, RoundedCornerShape(60.dp)),
                        shape = RoundedCornerShape(60.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Green300, disabledBackgroundColor = Green300, disabledContentColor = Color.Black),
                    ) {

                        Text(text = "Done", style = MaterialTheme.typography.h5,  textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }

}