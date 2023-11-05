package coroutines.console

import android.os.Build
import androidx.annotation.RequiresApi
import coroutines.webapi.ToDo
import coroutines.webapi.ToDoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
suspend fun main() {
    val job = CoroutineScope(Dispatchers.IO).launch {
        val toDos = ToDoService.getToDos()
        println(">> ToDos\n: $toDos")

        val toDo1 = ToDoService.getToDo(1)
        println(">> ToDos\n: $toDo1")

        var toDo = ToDo(title = "Study Coroutines")
        toDo = ToDoService.addToDo(toDo)
        println(">> New ToDo \n: $toDo")

        val isOk = ToDoService.deleteToDo(1)
        println(">> Was delete Ok \n: $isOk")
    }

    // Wait for the job to finish otherwise main will exit
    job.join()
}