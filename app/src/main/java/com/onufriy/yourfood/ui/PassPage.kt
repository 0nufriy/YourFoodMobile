package com.onufriy.yourfood.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.onufriy.yourfood.response.FridgeResponse
import com.onufriy.yourfood.response.PassesResponse
import com.onufriy.yourfood.ui.theme.*

@Composable
fun PassPage(nav: NavController){
    val client = YourFoodAPI.create()

    val Pass = produceState<List<PassesResponse>>(
        initialValue = emptyList(),
        producer = {
            value = client.getAllPass()

        })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Red100),
    ){
        Column(modifier = Modifier
            .fillMaxSize()) {
            Text(
                text = "Food Pass",
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )

            LazyRow(modifier = Modifier

                .padding(top = 60.dp)
                .fillMaxWidth(1f)
                .fillMaxHeight(0.8f)
                .background(color = Red200),
                verticalAlignment = Alignment.CenterVertically){
                itemsIndexed(Pass.value){ index, item ->
                    if(index == 0){
                        Spacer(modifier = Modifier.width(20.dp))
                    }

                    PassCard(nav,item.passid, item.image,item.passname,item.price,item.description)


                    if(index+1 == Pass.value.size){
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
            }


        }
    }
}

@Composable
fun PassCard(nav: NavController,id :Int,image: String?,passName: String, price: Int, description: String){


    Card(  modifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .border(2.dp, Color.Black, shape = RoundedCornerShape(25.dp)),
        backgroundColor = Red300,
        shape = RoundedCornerShape(25.dp),) {
        Box(modifier = Modifier
            .size(320.dp, 450.dp),
        contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(painter = rememberAsyncImagePainter(image),
                    contentDescription = "Pass",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = passName.trim(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                Text(
                    text = "Price: " + price.toString(),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = description,
                    maxLines = 2,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 20.dp)
                        .height(60.dp)
                )
                Button(
                    onClick = { nav.navigate("passInfo/" + id.toString())},
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(top = 10.dp)
                        .fillMaxWidth(0.7f)
                        .height(50.dp)
                        .border(2.dp, Color.Black, RoundedCornerShape(60.dp)),
                    shape = RoundedCornerShape(60.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Red200),
                ) {

                    Text(text = "View detail", style = MaterialTheme.typography.h5,  textAlign = TextAlign.Center)
                }

            }
        }
    }
}