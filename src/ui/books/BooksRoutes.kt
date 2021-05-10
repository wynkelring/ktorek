package com.example.ui.books

import com.example.repository.BooksRepository
import com.example.ui.Endpoints
import com.example.ui.login.Session
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import io.ktor.sessions.*

fun Route.books() {

    val repository = BooksRepository()

    get(Endpoints.BOOKS.url) {
        call.respondHtmlTemplate(
            BooksTemplate(
                call.sessions.get<Session>(),
                repository.getAllBooks()
            )
        ) {
        }
    }
}
