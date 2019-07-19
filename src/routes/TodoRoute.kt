package com.littlefireflies.routes

import com.littlefireflies.model.NewTodo
import com.littlefireflies.model.Response
import com.littlefireflies.service.TodoService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.todo(todoService: TodoService) {
    route("/todo") {
        get {
            call.respond(todoService.getAllTodos())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: 0

            val todo = todoService.getTodoById(id)
            if (todo == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(todo)
        }

        post {
            val todo = call.receive<NewTodo>()
            val inserted = todoService.addTodo(todo)

            if (inserted != null) {
                call.respond(inserted)
            } else {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: 0
            val todo = call.receive<NewTodo>()
            val updated = todoService.updateTodo(id, todo)

            if (updated != null) {
                call.respond(updated)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: 0
            val success = todoService.deleteTodo(id)

            if (success) {
                call.respond(Response(message = "Delete successful"))
            } else {
                call.respond(HttpStatusCode.NotFound, "Delete Failed")
            }
        }
    }
}