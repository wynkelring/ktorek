package com.example.ui.editBook

import com.example.repository.BooksRepository
import com.example.repository.UsersRepository
import com.example.ui.Endpoints
import com.example.ui.login.Session
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.slf4j.LoggerFactory

fun Route.editBook() {

    val repository = BooksRepository()
    val userRepository = UsersRepository()

    get(Endpoints.EDITBOOK.url) {
        val id = call.parameters["id"]?.toIntOrNull()

        if (id == null || call.sessions.get<Session>() == null) {
            call.respondRedirect(Endpoints.BOOKS.url)
        } else {
            val book = repository.getBooks(id)
            if (book != null) {
                call.respondHtmlTemplate(EditBookTemplate(call.sessions.get<Session>(), book)) {}
            } else {
                call.respondRedirect(Endpoints.BOOKS.url)
            }
        }
    }

    post(Endpoints.EDITBOOK.url) {
        val id = call.parameters["id"]?.toIntOrNull()

        val session = call.sessions.get<Session>();
        if (session != null && id != null) {
            val book = repository.getBooks(id)
            val username = session.username
            val user = userRepository.getUserEntityByUsername(username)
            if (book != null && user != null) {
                val log = LoggerFactory.getLogger("AddBookView")
                val multipart = call.receiveMultipart()
                while (true) {
                    val part = multipart.readPart() ?: break
                    when (part) {
                        is PartData.FormItem -> {
                            log.info("FormItem: ${part.name} = ${part.value}")
                            if (part.name == "title")
                                book.title = part.value
                            if (part.name == "author")
                                book.author = part.value
                            if (part.name == "price")
                                book.price = part.value.toFloat()
                        }
                        is PartData.FileItem -> {
                            log.info("FileItem: ${part.name} -> ${part.originalFileName} of ${part.contentType}")
                        }
                    }
                    part.dispose()
                }
                repository.updateBooks(id, book)
                call.respondRedirect(Endpoints.BOOKS.url)
            } else {
                call.respondRedirect(Endpoints.BOOKS.url)
            }
        } else {
            call.respondRedirect(Endpoints.LOGIN.url)
        }
    }
}
