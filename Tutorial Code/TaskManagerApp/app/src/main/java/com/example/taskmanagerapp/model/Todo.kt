package com.example.taskmanagerapp.model

import java.time.LocalDateTime

data class Todo(val task: String, val priority: Int, val dueDate : LocalDateTime)
