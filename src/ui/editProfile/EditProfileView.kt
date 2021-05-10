package com.example.ui.editProfile

import com.example.SecurityHandler
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

fun Route.editProfileView() {

    var repository: UsersRepository = UsersRepository()

    get(Endpoints.PROFILE.url) {
        val session = call.sessions.get<Session>()
        if (session != null) {
            val user = repository.getUserByUsername(session.username)
            if (user != null) {
                call.respondHtmlTemplate(EditProfileTemplate(session, user.fullName)) {}
            } else {
                call.respondHtmlTemplate(EditProfileTemplate(session, "")) {}
            }
        } else {
            call.respondRedirect(Endpoints.LOGIN.url)
        }
    }

    post(Endpoints.PROFILE.url) {
        val log = LoggerFactory.getLogger("EditProfileView")
        val session = call.sessions.get<Session>()
        val multipart = call.receiveMultipart()
        var fullname: String = ""
        var password: String = ""
        while (true) {
            val part = multipart.readPart() ?: break
            when (part) {
                is PartData.FormItem -> {
                    log.info("FormItem: ${part.name} = ${part.value}")
                    if (part.name == "fullname")
                        fullname = part.value
                    if (part.name == "password")
                        password = part.value
                }
                is PartData.FileItem -> {
                    log.info("FileItem: ${part.name} -> ${part.originalFileName} of ${part.contentType}")
                }
            }
            part.dispose()
        }
        if (session != null) {
            val user = repository.getUserByUsername(session.username)
            if (user != null) {
                if (password == "") {
                    user.fullName = fullname
                    user.id?.let { it1 -> repository.updateUsers(it1, user) }
                    call.respondHtmlTemplate(
                        EditProfileTemplate(session, fullname)
                    ) {
                        greeting {
                            +"Data saved"
                        }
                    }
                } else {
                    if (SecurityHandler.isValidPassword(password)) {
                        user.password = SecurityHandler.hashPassword(password)
                        user.id?.let { it1 -> repository.updateUsers(it1, user) }
                        call.respondHtmlTemplate(
                            EditProfileTemplate(session, fullname)
                        ) {
                            greeting {
                                +"Data saved"
                            }
                        }
                    } else {
                        call.respondHtmlTemplate(EditProfileTemplate(session, user.fullName)) {
                            greeting {
                                +"Password too weak"
                            }
                        }
                    }
                }
            }
        } else {
            call.respondHtmlTemplate(LoginTemplate(call.sessions.get<Session>())) {
            }
        }
    }
}
