package com.littlefireflies

import com.littlefireflies.routes.todo
import com.littlefireflies.service.DatabaseFactory
import com.littlefireflies.service.TodoService
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.Routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty


fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    DatabaseFactory.init()

    val todoService = TodoService()

    install(Routing) {
        todo(todoService)
    }
}

fun main() {
    embeddedServer(Netty, 8000, watchPaths = listOf("Main.kt"), module = Application::module).start(true)
}