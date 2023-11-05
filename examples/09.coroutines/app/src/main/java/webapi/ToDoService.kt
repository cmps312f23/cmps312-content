package coroutines.webapi

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ToDo (
    val id: Int = 0,
    val title: String,
    val userId: Int = 1,
    val completed: Boolean = false)

object ToDoService {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/todos"

    private val client = HttpClient() {
        //This will auto-parse from/to json when sending and receiving data from the Web API
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }

        //Log HTTP request/response details for debugging
        install(Logging) {
             level = LogLevel.INFO
            //level = LogLevel.ALL
        }
    }

    suspend fun getToDos(): List<ToDo> {
        return client.get(BASE_URL).body()
    }

    suspend fun getToDo(toDoId: Int): ToDo {
        val url = "$BASE_URL/$toDoId"
        return client.get(url).body()
    }

    suspend fun addToDo(toDo: ToDo): ToDo {
        return client.post(BASE_URL) {
            contentType(ContentType.Application.Json)
            setBody(toDo)
        }.body()
    }

    suspend fun updateToDo(toDo: ToDo): ToDo {
        val url = "$BASE_URL/${toDo.id}"
        return client.put(url) {
            contentType(ContentType.Application.Json)
            setBody(toDo)
        }.body()
    }

    suspend fun deleteToDo(toDoId: Int): Boolean {
        val url = "$BASE_URL/$toDoId"
        val response = client.delete(url)
        return (response.status == HttpStatusCode.OK)
    }
}