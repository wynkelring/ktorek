package com.example.ui.editProfile

import com.example.GeneralViewTemplate
import com.example.ui.Endpoints
import com.example.ui.login.Session
import io.ktor.html.*
import kotlinx.html.*

class EditProfileTemplate(val session: Session?, val fullname: String) : Template<HTML> {
    val basicTemplate: GeneralViewTemplate = GeneralViewTemplate(session)
    val greeting = Placeholder<FlowContent>()
    override fun HTML.apply() {
        insert(basicTemplate) {

            content {
                div(classes = "mt-2") {
                    p {
                        insert(greeting)
                    }
                }
                form(
                    method = FormMethod.post,
                    encType = FormEncType.multipartFormData,
                    action = Endpoints.PROFILE.url
                ) {
                    div(classes = "mb-3") {
                        input(type = InputType.text, classes = "form-control", name = "fullname") {
                            this.placeholder = "Type in your full name here..."
                            this.attributes.put("aria-label", "Full name")
                            this.attributes.put("aria-describedby", "basic-addon1")
                            this.value = fullname
                        }
                    }
                    div(classes = "mb-3") {
                        input(type = InputType.password, classes = "form-control", name = "password") {
                            this.placeholder = "Type in your password here (leave blank if nothing changed)..."
                            this.attributes.put("aria-label", "Password")
                            this.attributes.put("aria-describedby", "basic-addon1")
                        }
                    }
                    div(classes = "mb-3") {
                        button(classes = "btn btn-primary", type = ButtonType.submit) {
                            +"Save"
                        }
                    }
                }
            }
        }
    }
}
