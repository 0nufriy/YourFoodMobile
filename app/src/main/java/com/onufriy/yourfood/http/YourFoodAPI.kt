package com.onufriy.yourfood.http

import com.onufriy.yourfood.request.*
import com.onufriy.yourfood.response.FridgeResponse
import com.onufriy.yourfood.response.OrdersResponse
import com.onufriy.yourfood.response.PassInfoResponse
import com.onufriy.yourfood.response.PassesResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.*
import java.util.LinkedList

interface YourFoodAPI {
    suspend fun login(request: LoginRequest) : HttpStatusCode
    suspend fun register(request: RegisterRequest) : HttpStatusCode
    suspend fun getUserInfo(login: String) : UserInfo
    suspend fun getAllFridge() : List<FridgeResponse>
    suspend fun getFridgeByAddress(address: String): List<FridgeResponse>
    suspend fun updateFridge(request: UpdateFridge) : HttpStatusCode
    suspend fun getUserOrders(login: String) : MutableList<OrdersResponse>
    suspend fun updateOrderStatus(request: UpdateOrderStatus) : HttpStatusCode
    suspend fun getAllPass() : List<PassesResponse>
    suspend fun getPassInfo(id: Int): PassInfoResponse
    suspend fun updatePass(request: UpdatePass): HttpStatusCode
    suspend fun getPassIdByLogin(login: String) : Int

    companion object {
        fun create() : YourFoodAPI {
            return  YourFoodApiImpl(
                client = HttpClient(Android){
                    install(Logging){
                        level = LogLevel.ALL
                    }
                    install(JsonFeature){
                        serializer = KotlinxSerializer()
                    }

                }
            )
        }
    }
}