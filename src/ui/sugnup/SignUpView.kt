package com.example.ui.sugnup

import com.example.SecurityHandler
import com.example.model.Users
import com.example.repository.UsersRepository
import com.example.ui.Endpoints
import com.example.ui.login.LoginTemplate
import com.example.ui.login.Session
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.slf4j.LoggerFactory

fun Route.signUpView() {

    val repository = UsersRepository()

    get(Endpoints.SIGNUP.url) {
        if (call.sessions.get<Session>() == null) {
            call.respondHtmlTemplate(SignUpTemplate(call.sessions.get<Session>())) {}
        } else {
            call.respondRedirect(Endpoints.HOME.url)
        }
    }

    post(Endpoints.SIGNUP.url) {
        val log = LoggerFactory.getLogger("SignUpView")
        val multipart = call.receiveMultipart()
        var fullname: String = ""
        var username: String = ""
        var password: String = ""
        while (true) {
            val part = multipart.readPart() ?: break
            when (part) {
                is PartData.FormItem -> {
                    log.info("FormItem: ${part.name} = ${part.value}")
                    if (part.name == "fullname")
                        fullname = part.value
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
        if (repository.getUserByUsername(username) == null) {
            if (SecurityHandler.isValidPassword(password)) {
                repository.addUsers(Users(null, username, password, fullname))
                call.respondHtmlTemplate(
                    LoginTemplate(call.sessions.get<Session>())
                ) {
                    greeting {
                        +"You can log in now"
                    }
                }
            } else {
                call.respondHtmlTemplate(SignUpTemplate(call.sessions.get<Session>())) {
                    greeting {
                        +"Password too weak"
                    }
                }
            }
        } else {
            call.respondHtmlTemplate(SignUpTemplate(call.sessions.get<Session>())) {
                greeting {
                    +"Username already exists... Try again."
                }
            }
        }
    }
}
