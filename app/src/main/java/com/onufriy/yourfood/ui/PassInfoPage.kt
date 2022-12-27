package com.onufriy.yourfood.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
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
import coil.compose.rememberAsyncImagePainter
import com.onufriy.yourfood.R
import com.onufriy.yourfood.http.YourFoodAPI
import com.onufriy.yourfood.request.LoginRequest
import com.onufriy.yourfood.request.UpdatePass
import com.onufriy.yourfood.request.UserInfo
import com.onufriy.yourfood.response.PassInfoResponse
import com.onufriy.yourfood.response.PassesResponse
import com.onufriy.yourfood.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun PassInfpoPage(nav: NavController, id: String, login: String){

    val client = YourFoodAPI.create()
    val trueID = id.toInt()
    val text = remember{
        mutableStateOf("Unselect")
    }
    val remove = remember {
        mutableStateOf(true)
    }


    val Pass = produceState<PassInfoResponse>(
        initialValue = PassInfoResponse(0,0,"loading","loading","loading", emptyList()),
        producer = {

            val UserPass = client.getPassIdByLogin(login)
            if(UserPass.toString() != id){
                text.value = "Select"
                remove.value = false;
            }


            value = client.getPassInfo(trueID)
        })

    val coroutineScope = rememberCoroutineScope()


    fun update(id1: Int){
        val UpdatePass = UpdatePass(id1, login)
        coroutineScope.launch {

            val update1 = client.updatePass(UpdatePass)
            if (update1.value == 200){
                nav.navigate("pass")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Red100),
    ){
        Column(modifier = Modifier
            .fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                Box(modifier = Modifier.padding(start = 0.dp, top = 10.dp)) {
                    IconButton(onClick = {  nav.navigate("pass") } ) {
                        Icon(imageVector = Icons.Filled.ArrowBack
                            ,"Back"
                            , modifier = Modifier.size(50.dp))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Image(painter = rememberAsyncImagePainter(Pass.value.image),
                        contentDescription = "Pass",
                        modifier = Modifier
                            .size(90.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Column() {
                        Text(
                            text = Pass.value.passName.trim(),
                            style = MaterialTheme.typography.h3,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "Price: " + Pass.value.price.toString(),
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }


                }
            }

            Button(
                onClick = {
                    if(remove.value){
                       update(0)
                    }else{
                        update(trueID)
                    }

                          },
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(60.dp)),
                shape = RoundedCornerShape(60.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Red300),
            ) {

                Text(text = text.value, style = MaterialTheme.typography.h5,  textAlign = TextAlign.Center)
            }
            Text(
                text = Pass.value.description.trim(),
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 30.dp, vertical = 20.dp)
            )
            LazyColumn(){
                itemsIndexed(Pass.value.foodInfo){ index, item ->
                    FoodCard(item.image,item.name,item.description)

                    if(index+1 == Pass.value.foodInfo.size){
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }



        }
    }
}

@Composable
fun FoodCard(image: String, name: String, descr: String){
    Card(  modifier = Modifier
        .padding(top = 5.dp, start = 20.dp, end = 20.dp)
        .fillMaxWidth(),
        backgroundColor = Red300,
        shape = RoundedCornerShape(25.dp),){
        Box(modifier = Modifier
            .size(0.dp, 110.dp)
            .fillMaxWidth()){
                Column() {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .padding(start = 20.dp, top = 1.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = rememberAsyncImagePainter(image),
                            contentDescription = "Pass",
                            modifier = Modifier
                                .padding(start = 15.dp)
                                .size(70.dp)
                                .border(1.dp, Color.Black)


                        )
                        Text(
                            text = descr,
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 1.dp, top = 1.dp)
                        )
                    }

                }
        }
    }
}