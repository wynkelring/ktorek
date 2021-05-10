package com.example.ui.book

import com.example.repository.BooksRepository
import com.example.ui.Endpoints
import com.example.ui.books.BookTemplate
import com.example.ui.login.Session
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

fun Route.book() {

    val repository = BooksRepository()

    get(Endpoints.BOOK.url.plus("/{id}")) {
        val id = call.parameters["id"]?.toIntOrNull()

        if (id == null) {
            call.respondHtmlTemplate(
                BookTemplate(
                    call.sessions.get<Session>(),
                    null
                )
            ) {
            }
        }

        val book = id?.let { it1 -> repository.getBooks(it1) }

        call.respondHtmlTemplate(
            BookTemplate(
                call.sessions.get<Session>(),
                book
            )
        ) {
        }
    }

    post(Endpoints.DELETEBOOK.url) {
        val id = call.parameters["id"]?.toIntOrNull()

        val session = call.sessions.get<Session>();
        if (session != null && id != null) {
            if (repository.getBooks(id) != null) {
                repository.removeBooks(id)
            }
        }
        call.respondRedirect(Endpoints.BOOKS.url)
    }
}
