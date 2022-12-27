package com.onufriy.yourfood

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.onufriy.yourfood.ui.*
import com.onufriy.yourfood.ui.theme.*


@Composable
fun MainPage(nav: NavController, login: String){
        val navController = rememberNavController()
        var order = remember {
                mutableStateOf(R.drawable.ordersselect)
        }
        var profile = remember {
                mutableStateOf(R.drawable.profileunselect)
        }
        var pass = remember {
                mutableStateOf(R.drawable.passunselect)
        }
        Scaffold(
                bottomBar = {
                        BottomNavigation(
                                modifier = Modifier.size(height = 90.dp, width = 9999.dp),
                                backgroundColor = Red200
                        ) {
                                BottomNavigationItem(selected = false,
                                        onClick = { navController.navigate("pass")},
                                        label = {Text("Pass")},
                                        icon = {Icon(
                                                painter = painterResource(id = pass.value),
                                                contentDescription = null,
                                                modifier = Modifier.size(50.dp)
                                        )})

                                BottomNavigationItem(selected = false,
                                        onClick = { navController.navigate("orders") },
                                        label = {Text("Order")},
                                        icon = {Icon(
                                                painter = painterResource(id = order.value),
                                                contentDescription = null,
                                                modifier = Modifier.size(50.dp)
                                        )})


                                BottomNavigationItem(selected = false,
                                        onClick = { navController.navigate("profile") },
                                        label = {Text("Profile")},
                                        icon = {Icon(
                                                painter = painterResource(id = profile.value),
                                                contentDescription = null,
                                                modifier = Modifier.size(50.dp)
                                        )})
                        }
                }
        ) {
                NavHost(navController = navController, startDestination = "orders"){
                        composable("orders"){
                                pass.value = R.drawable.passunselect
                                profile.value = R.drawable.profileunselect
                                order.value = R.drawable.ordersselect
                                OrderPage(navController, login)
                        }
                        composable("profile"){
                                pass.value = R.drawable.passunselect
                                profile.value = R.drawable.profileselect
                                order.value = R.drawable.ordersunselect
                                ProfilePage(nav = navController,nav,login = login)
                        }
                        composable("pass"){
                                pass.value = R.drawable.passselect
                                profile.value = R.drawable.profileunselect
                                order.value = R.drawable.ordersunselect
                                PassPage(nav = navController)
                        }
                        composable("chnageFridge/{id}",
                                arguments = listOf(navArgument("id"){
                                        type = NavType.StringType
                                })){
                                FridgePage(nav = navController,
                                        it.arguments?.getString("id").toString(), login
                                )
                        }
                        composable("passInfo/{id}",
                                arguments = listOf(navArgument("id"){
                                        type = NavType.StringType
                                })){
                                PassInfpoPage(nav = navController,
                                        it.arguments?.getString("id").toString(),login)
                        }
                }
        }

}