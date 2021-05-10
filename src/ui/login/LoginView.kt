package com.example.ui.login

import com.example.Constants
import com.example.SecurityHandler
import com.example.ui.Endpoints
import com.example.ui.home.HomeTemplate
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.slf4j.LoggerFactory
import java.util.*

data class Session(val username: String, val uniqueID: String = UUID.randomUUID().toString())

fun Route.loginView() {
    get(Endpoints.LOGIN.url) {
        if (call.sessions.get<Session>() == null) {
            call.respondHtmlTemplate(LoginTemplate(call.sessions.get<Session>())) {
            }
        } else {
            call.respondRedirect(Endpoints.HOME.url)
        }
    }
    get(Endpoints.HOME.url) {
        call.respondHtmlTemplate(HomeTemplate(call.sessions.get<Session>())) {}
    }

    get(Endpoints.LOGOUT.url) {
        if (call.sessions.get<Session>() != null) {
            call.sessions.clear(Constants.COOKIE_NAME.value)
            call.respondHtmlTemplate(LogoutTemplate(call.sessions.get<Session>())) {}
        } else {
            call.respondRedirect(Endpoints.LOGIN.url)
        }
    }

    post(Endpoints.LOGIN.url) {
        val log = LoggerFactory.getLogger("LoginView")
        val multipart = call.receiveMultipart()
        var username: String = ""
        var password: String = ""
        while (true) {
            val part = multipart.readPart() ?: break
            when (part) {
                is PartData.FormItem -> {
                    log.info("FormItem: ${part.name} = ${part.value}")
                    if (part.name == "username")
                        username = part.value
                    if (part.name == "password")
                        password = part.value
                }
                is PartData.FileItem -> {
                    log.info("FileItem: ${part.name} -> ${part.originalFileName} of ${part.contentType}")
                }
            }
            part.dispose()
        }
        if (SecurityHandler().isValid(username, password)) {
            call.sessions.set(Constants.COOKIE_NAME.value, Session(username))
            call.respondRedirect(Endpoints.BOOKS.url)
        } else {
            call.respondHtmlTemplate(LoginTemplate(call.sessions.get<Session>())) {
                greeting {
                    +"Username or password was invalid... Try again."
                }
            }
        }
    }
}
