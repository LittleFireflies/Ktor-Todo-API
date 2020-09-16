package com.littlefireflies.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.date

object TodoTable : Table(name = "todo") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val date = date("date")
    val done = bool("done")

    override val primaryKey = PrimaryKey(id)
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