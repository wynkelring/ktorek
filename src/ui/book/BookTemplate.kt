package com.example.ui.books

import com.example.GeneralViewTemplate
import com.example.model.Books
import com.example.ui.Endpoints
import com.example.ui.login.Session
import io.ktor.html.*
import kotlinx.html.*

class BookTemplate(val session: Session?, val book: Books?) : Template<HTML> {
    val basicTemplate: GeneralViewTemplate = GeneralViewTemplate(session)
    override fun HTML.apply() {
        insert(basicTemplate) {

            content {
                div(classes = "mt-2") {
                    h2() {
                        +"Books available"
                    }

                    table(classes = "table table-striped") {
                        thead {
                            tr {
                                th(scope = ThScope.col) { +"Id" }
                                th(scope = ThScope.col) { +"Title" }
                                th(scope = ThScope.col) { +"Author" }
                                th(scope = ThScope.col) { +"Price" }
                                th(scope = ThScope.col) { +"" }
                            }
                        }
                        tbody {
                            if (book != null) {
                                tr {
                                    td { +"${book.id}" }
                                    td { +"${book.title}" }
                                    td { +"${book.author}" }
                                    td { +"${book.price}" }
                                    td {
                                        if (session != null) {
                                            form(
                                                method = FormMethod.get,
                                                encType = FormEncType.multipartFormData,
                                                action = Endpoints.BOOK.url.plus("/" + book.id + "/edit")
                                            ) {
                                                button(classes = "btn btn-success", type = ButtonType.submit) {
                                                    +"Edit"
                                                }
                                            }

                                            form(
                                                method = FormMethod.post,
                                                encType = FormEncType.multipartFormData,
                                                action = Endpoints.BOOK.url.plus("/" + book.id + "/delete")
                                            ) {
                                                button(classes = "btn btn-success", type = ButtonType.submit) {
                                                    +"Delete"
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}
