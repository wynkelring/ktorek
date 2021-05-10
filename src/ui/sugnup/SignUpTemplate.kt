package com.example.ui.sugnup

import com.example.GeneralViewTemplate
import com.example.ui.Endpoints
import com.example.ui.login.Session
import io.ktor.html.*
import kotlinx.html.*

class SignUpTemplate(val session: Session?) : Template<HTML> {
    val basicTemplate: GeneralViewTemplate = GeneralViewTemplate(session)
    val greeting = Placeholder<FlowContent>()
    override fun HTML.apply() {
        insert(basicTemplate) {

            content {
                div(classes = "mt-2") {
                    h2() {
                        +"Welcome to the \"Bookstore\""
                    }
                    p {
                        insert(greeting)
                    }
                }
                form(
                    method = FormMethod.post,
                    encType = FormEncType.multipartFormData,
                    action = Endpoints.SIGNUP.url
                ) {
                    div(classes = "mb-3") {
                        input(type = InputType.text, classes = "form-control", name = "fullname") {
                            this.placeholder = "Type in your full name here..."
                            this.attributes.put("aria-label", "Full name")
                            this.attributes.put("aria-describedby", "basic-addon1")
                        }
                    }
                    div(classes = "mb-3") {
                        input(type = InputType.text, classes = "form-control", name = "username") {
                            this.placeholder = "Type in your username here..."
                            this.attributes.put("aria-label", "Username")
                            this.attributes.put("aria-describedby", "basic-addon1")
                        }
                    }
                    div(classes = "mb-3") {
                        input(type = InputType.password, classes = "form-control", name = "password") {
                            this.placeholder = "Type in your password here..."
                            this.attributes.put("aria-label", "Password")
                            this.attributes.put("aria-describedby", "basic-addon1")
                        }
                    }
                    div(classes = "mb-3") {
                        button(classes = "btn btn-primary", type = ButtonType.submit) {
                            +"SignUp"
                        }
                    }
                }
            }
        }
    }
}
