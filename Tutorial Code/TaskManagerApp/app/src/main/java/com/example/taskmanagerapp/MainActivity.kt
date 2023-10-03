package com.example.taskmanagerapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanagerapp.model.Todo
import com.example.taskmanagerapp.ui.theme.TaskManagerAppTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyTodoApp()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditScreen(onAddTodo: (Todo) -> Unit) {
    var task by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(0) }

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(Modifier.padding(6.dp)) {
            OutlinedTextField(
                value = task,
                onValueChange = { task = it },
                label = { Text("Add task") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )

//            Priority Drop Down
            PriorityDropDown(priorities = listOf(1, 2, 3)) {
                priority = it
            }
            ElevatedButton(
                onClick = {
                    val todo = Todo(
                        task = task,
                        priority = priority,
                        LocalDateTime.now()
                    )
                    onAddTodo(todo)
                    task = ""
                    priority = 2
                },
                modifier = Modifier.align(Alignment.End),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Add To Do")
            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityDropDown(priorities: List<Int>, updatePriority: (Int) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedPriority by remember { mutableStateOf("Select priority") }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }) {

        TextField(
            value = selectedPriority,
            onValueChange = { selectedPriority = it },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            readOnly = true
        )

        ExposedDropdownMenu(expanded = isExpanded,
            onDismissRequest = { isExpanded = false }) {

            priorities.forEach {
                DropdownMenuItem(text = { Text(text = it.toString()) },
                    onClick = {
                        isExpanded = false
                        selectedPriority = it.toString()
                        updatePriority(it)
                    }
                )
            }

        }
    }
}

@Composable
fun TodoList(todos: List<Todo>, deleteTodo: (Todo) -> Unit) {
    Column {
        LazyColumn {
            items(todos) {
                TodoItem(it, deleteTodo)
            }
        }
    }
}

@Composable
fun TodoItem(todo: Todo, onDelete: (Todo) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = todo.task,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Due Date ${todo.dueDate}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)

            )
            Text(
                text = "Priority ${todo.priority}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Button(onClick = {
                onDelete(todo)
            }, modifier = Modifier.align(Alignment.End)) {
                Text(text = "Delete")
            }

        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyTodoApp() {
    var todos by remember {
        mutableStateOf(
            listOf<Todo>(
                Todo(task = "Buy Milk", priority = 1, LocalDateTime.now()),
                Todo(task = "Buy Bread", priority = 2, LocalDateTime.now()),
                Todo(task = "Buy Eggs", priority = 3, LocalDateTime.now()),
                Todo(task = "Buy Sugar", priority = 1, LocalDateTime.now()),
                Todo(task = "Buy Banana", priority = 2, LocalDateTime.now()),
                Todo(task = "Do Homework", priority = 3, LocalDateTime.now()),
            )
        )
    }

    todos = todos.toMutableList().sortedBy { it.priority }

    Scaffold(
        topBar = {
            TodoEditScreen() {
                todos = todos + it
            }
        },
    ) {
        Column(Modifier.padding(it)) {
            TodoList(todos) { todo ->
                todos = todos.toMutableList().also { it.remove(todo) }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TodoListPreview() {
    TaskManagerAppTheme {
        TodoList(
            listOf(
                Todo(task = "Task", priority = 1, LocalDateTime.now()),
                Todo(task = "Task", priority = 2, LocalDateTime.now()),
                Todo(task = "Task", priority = 1, LocalDateTime.now()),
                Todo(task = "Task", priority = 3, LocalDateTime.now()),
            )
        ) {

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TodoItemPreview() {
    TaskManagerAppTheme {
        TodoItem(Todo(task = "Task", priority = 1, LocalDateTime.now())) {

        }
    }
}

@Preview
@Composable
fun PriorityDropDownPreview() {
    TaskManagerAppTheme {
        PriorityDropDown(listOf(1, 2, 3)) {

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TodoEditScreenPreview() {
    TaskManagerAppTheme {
        TodoEditScreen(){

        }
    }
}

