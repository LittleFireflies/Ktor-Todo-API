package com.littlefireflies.service

import com.littlefireflies.model.NewTodo
import com.littlefireflies.model.Todo
import com.littlefireflies.model.TodoTable
import com.littlefireflies.service.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime

class TodoService {

    suspend fun getAllTodos(): List<Todo> = dbQuery {
        TodoTable.selectAll().orderBy(TodoTable.date to SortOrder.DESC).map { it.toModel() }
    }

    suspend fun getTodoById(id: Int): Todo? = dbQuery {
        TodoTable.select { TodoTable.id eq id }.mapNotNull { it.toModel() }.singleOrNull()
    }

    suspend fun addTodo(todo: NewTodo): Todo? {
        var key = 0
        dbQuery {
            key = (TodoTable.insert {
                it[name] = todo.name
                it[date] = DateTime.parse(todo.date)
                it[done] = todo.done
            } get TodoTable.id)
        }

        return getTodoById(key)
    }

    suspend fun updateTodo(id: Int, todo: NewTodo): Todo? {
        dbQuery {
            TodoTable.update({ TodoTable.id eq id }) {
                it[name] = todo.name
                it[date] = DateTime(todo.date)
                it[done] = todo.done
            }
        }

        return getTodoById(id)
    }

    suspend fun deleteTodo(id: Int): Boolean = dbQuery {
        TodoTable.deleteWhere { TodoTable.id eq id } > 0
    }

    private fun ResultRow.toModel(): Todo {
        return Todo(
            id = this[TodoTable.id],
            name = this[TodoTable.name],
            date = this[TodoTable.date].toString(),
            done = this[TodoTable.done]
        )
    }
}