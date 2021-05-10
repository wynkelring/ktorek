package com.example.ui.addBook

import com.example.GeneralViewTemplate
import com.example.ui.Endpoints
import com.example.ui.login.Session
import io.ktor.html.*
import kotlinx.html.*

class AddBookTemplate(val session: Session?) : Template<HTML> {
    val basicTemplate: GeneralViewTemplate = GeneralViewTemplate(session)
    val greeting = Placeholder<FlowContent>()
    override fun HTML.apply() {
        insert(basicTemplate) {

            content {
                div(classes = "mt-2") {
                    h2() {
                        +"Add new book"
                    }
                    p {
                        insert(greeting)
                    }
                }
                form(
                    method = FormMethod.post,
                    encType = FormEncType.multipartFormData,
                    action = Endpoints.ADDBOOK.url
                ) {
                    div(classes = "mb-3") {
                        input(type = InputType.text, classes = "form-control", name = "title") {
                            this.placeholder = "Type in title here..."
                            this.attributes.put("aria-label", "Title")
                            this.attributes.put("aria-describedby", "basic-addon1")
                        }
                    }
                    div(classes = "mb-3") {
                        input(type = InputType.text, classes = "form-control", name = "author") {
                            this.placeholder = "Type in author here..."
                            this.attributes.put("aria-label", "Author")
                            this.attributes.put("aria-describedby", "basic-addon1")
                        }
                    }
                    div(classes = "mb-3") {
                        input(type = InputType.number, classes = "form-control", name = "price") {
                            this.placeholder = "Type in your price here..."
                            this.attributes.put("step", "0.01")
                            this.attributes.put("aria-label", "Price")
                            this.attributes.put("aria-describedby", "basic-addon1")
                        }
                    }
                    div(classes = "mb-3") {
                        button(classes = "btn btn-primary", type = ButtonType.submit) {
                            +"Add book"
                        }
                    }
                }
            }
        }
    }
}
