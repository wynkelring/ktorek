package com.example.ui.addBook

import com.example.model.Books
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

fun Route.addBook() {

    val repository = BooksRepository()
    val userRepository = UsersRepository()

    get(Endpoints.ADDBOOK.url) {
        if (call.sessions.get<Session>() != null) {
            call.respondHtmlTemplate(AddBookTemplate(call.sessions.get<Session>())) {}
        } else {
            call.respondRedirect(Endpoints.BOOKS.url)
        }
    }

    post(Endpoints.ADDBOOK.url) {
        val session = call.sessions.get<Session>();
        if (session != null) {
            val log = LoggerFactory.getLogger("AddBookView")
            val multipart = call.receiveMultipart()
            var title: String = ""
            var author: String = ""
            var price: Double = 0.00
            while (true) {
                val part = multipart.readPart() ?: break
                when (part) {
                    is PartData.FormItem -> {
                        log.info("FormItem: ${part.name} = ${part.value}")
                        if (part.name == "title")
                            title = part.value
                        if (part.name == "author")
                            author = part.value
                        if (part.name == "price")
                            price = part.value.toDouble()
                    }
                    is PartData.FileItem -> {
                        log.info("FileItem: ${part.name} -> ${part.originalFileName} of ${part.contentType}")
                    }
                }
                part.dispose()
            }
            val username = session.username
            val user = userRepository.getUserEntityByUsername(username)
            if (user != null) {
                repository.addBooks(Books(null, title, author, price.toFloat(), user))
                call.respondRedirect(Endpoints.BOOKS.url)
            } else {
                call.respondRedirect(Endpoints.LOGIN.url)
            }
        } else {
            call.respondRedirect(Endpoints.LOGIN.url)
        }
    }
}
