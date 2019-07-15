package com.littlefireflies.model

import org.jetbrains.exposed.sql.Table

object TodoTable : Table(name = "todo") {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val date = date("date")
    val done = bool("done")
}

data class Todo(
    val id: Int,
    val name: String,
    val date: String,
    val done: Boolean
)

data class NewTodo(
    val name: String,
    val date: String,
    val done: Boolean
)