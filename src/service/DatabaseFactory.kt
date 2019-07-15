package com.littlefireflies.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(
            "jdbc:mysql://us-cdbr-iron-east-01.cleardb.net/heroku_8e048c86bee0a18",
            driver = "com.mysql.jdbc.Driver",
            user = "bb09bde0afe4ed",
            password = "Rahasia :P"
        )
    }

    suspend fun <T> dbQuery(block: () -> T): T {
        return withContext(Dispatchers.IO) {
            transaction { block() }
        }
    }
}