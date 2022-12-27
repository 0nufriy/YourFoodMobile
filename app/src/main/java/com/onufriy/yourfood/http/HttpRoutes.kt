package com.onufriy.yourfood.http

import com.onufriy.yourfood.request.UserInfo

object HttpRoutes {
    private const val BASE_URL = "http://10.0.2.2:5052"
    const val LOGIN = "$BASE_URL/User/auth"
    const val REGISTER = "$BASE_URL/User/register"
    const val UserInfo = "$BASE_URL/User/user-by-login?login="
    const val FrdidgeStatAdress = "$BASE_URL/Fridge/fridge-stat-by-adress?adress="
    const val FrdidgeStat = "$BASE_URL/Fridge/fridge-stat"
    const val UpdateFridge = "$BASE_URL/User/update-fridge"
    const val UserOrders = "$BASE_URL/Order/user-order?login="
    const val UpdateOrderStatus = "$BASE_URL/Order/update-status"
    const val getAllPass = "$BASE_URL/Pass/all-pass"
    const val getPassInfo = "$BASE_URL/Pass/pass-info?passid="
    const val updatePass = "$BASE_URL/User/update-pass"
    const val getPassIDByLogin = "$BASE_URL/Pass/pass-id-by-login?login="
}