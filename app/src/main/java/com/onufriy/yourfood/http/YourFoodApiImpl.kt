package com.onufriy.yourfood.http

import com.onufriy.yourfood.request.*
import com.onufriy.yourfood.response.FridgeResponse
import com.onufriy.yourfood.response.OrdersResponse
import com.onufriy.yourfood.response.PassInfoResponse
import com.onufriy.yourfood.response.PassesResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.util.*

class YourFoodApiImpl(
    private val client: HttpClient
): YourFoodAPI {
    override suspend fun login(request: LoginRequest): HttpStatusCode {
        return try {client.post{
            url(HttpRoutes.LOGIN)
            contentType(ContentType.Application.Json)
            body = request
            }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            HttpStatusCode.TemporaryRedirect
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            HttpStatusCode.BadRequest
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            HttpStatusCode.InternalServerError
        } catch(e: Exception) {
            println("Error: ${e.message}")
            HttpStatusCode.NotFound
        }

    }

    override suspend fun register(request: RegisterRequest): HttpStatusCode {
        return try{
            client.post{
                url(HttpRoutes.REGISTER)
                contentType(ContentType.Application.Json)
                body = request
            }
        }catch(e: RedirectResponseException) {
                // 3xx - responses
                println("Error: ${e.response.status.description}")
                HttpStatusCode.TemporaryRedirect
            } catch(e: ClientRequestException) {
                // 4xx - responses
                println("Error: ${e.response.status.description}")
                HttpStatusCode.BadRequest
            } catch(e: ServerResponseException) {
                // 5xx - responses
                println("Error: ${e.response.status.description}")
                HttpStatusCode.InternalServerError
            } catch(e: Exception) {
                println("Error: ${e.message}")
                HttpStatusCode.NotFound
            }
        }

    override suspend fun getUserInfo(login: String): UserInfo {
        return try{
            client.get{
                url(HttpRoutes.UserInfo + login)
            }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            UserInfo("Error","Error","Error","Error","Error","Error",1,"Error", 0.0)
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            UserInfo("Error","Error","Error","Error","Error","Error",1,"Error", 0.0)
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            UserInfo("Error","Error","Error","Error","Error","Error",1,"Error", 0.0)
        } catch(e: Exception) {
            println("Error: ${e.message}")
            UserInfo("Error","Error","Error","Error","Error","Error",1,"Error", 0.0)
        }
    }

    override suspend fun getAllFridge(): List<FridgeResponse> {
        return try{
            client.get{
                url(HttpRoutes.FrdidgeStat)
            }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch(e: Exception) {
            println("Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getFridgeByAddress(address: String): List<FridgeResponse> {
        return try{
            client.get{
                url(HttpRoutes.FrdidgeStatAdress + address)
            }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch(e: Exception) {
            println("Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun updateFridge(request: UpdateFridge): HttpStatusCode {
        return try {client.patch(){
            url(HttpRoutes.UpdateFridge)
            contentType(ContentType.Application.Json)
            body = request
        }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            HttpStatusCode.TemporaryRedirect
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            HttpStatusCode.BadRequest
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            HttpStatusCode.InternalServerError
        } catch(e: Exception) {
            println("Error: ${e.message}")
            HttpStatusCode.NotFound
        }
    }

    override suspend fun getUserOrders(login: String): MutableList<OrdersResponse> {
        return try{
            client.get{
                url(HttpRoutes.UserOrders+ login)
            }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            mutableListOf<OrdersResponse>()
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            mutableListOf<OrdersResponse>()
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            mutableListOf<OrdersResponse>()
        } catch(e: Exception) {
            println("Error: ${e.message}")
            mutableListOf<OrdersResponse>()
        }
    }

    override suspend fun updateOrderStatus(request: UpdateOrderStatus): HttpStatusCode {
        return try {client.patch(){
            url(HttpRoutes.UpdateOrderStatus)
            contentType(ContentType.Application.Json)
            body = request
        }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            HttpStatusCode.TemporaryRedirect
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            HttpStatusCode.BadRequest
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            HttpStatusCode.InternalServerError
        } catch(e: Exception) {
            println("Error: ${e.message}")
            HttpStatusCode.NotFound
        }
    }

    override suspend fun getAllPass(): List<PassesResponse> {
        return try{
            client.get{
                url(HttpRoutes.getAllPass)
            }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch(e: Exception) {
            println("Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getPassInfo(id: Int): PassInfoResponse {
        return try{
            client.get{
                url(HttpRoutes.getPassInfo+ id.toString())
            }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            PassInfoResponse(0,0,"loading","loading","loading", emptyList())
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            PassInfoResponse(0,0,"loading","loading","loading", emptyList())
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            PassInfoResponse(0,0,"loading","loading","loading", emptyList())
        } catch(e: Exception) {
            println("Error: ${e.message}")
            PassInfoResponse(0,0,"loading","loading","loading", emptyList())
        }
    }

    override suspend fun updatePass(request: UpdatePass): HttpStatusCode {
        return try {client.patch(){
            url(HttpRoutes.updatePass)
            contentType(ContentType.Application.Json)
            body = request
        }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            HttpStatusCode.TemporaryRedirect
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            HttpStatusCode.BadRequest
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            HttpStatusCode.InternalServerError
        } catch(e: Exception) {
            println("Error: ${e.message}")
            HttpStatusCode.NotFound
        }
    }

    override suspend fun getPassIdByLogin(login: String): Int {
        return try{
            client.get{
                url(HttpRoutes.getPassIDByLogin+ login)
            }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            0
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            0
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            0
        } catch(e: Exception) {
            println("Error: ${e.message}")
            0
        }
    }

}

