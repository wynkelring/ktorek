package com.example

import com.example.ui.addBook.addBook
import com.example.ui.book.book
import com.example.ui.books.books
import com.example.ui.editBook.editBook
import com.example.ui.editProfile.editProfileView
import com.example.ui.login.Session
import com.example.ui.login.loginView
import com.example.ui.sugnup.signUpView
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.sessions.*
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(Sessions) {
        cookie<Session>(Constants.COOKIE_NAME.value)
    }

    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError)
            throw cause
        }
    }

    install(Locations) {
    }

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(Authentication) {
        basic("bookStoreAuth") {
        }
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        books()
        book()
        addBook()
        editBook()
        loginView()
        signUpView()
        editProfileView()
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }
    }
}


