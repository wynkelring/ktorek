package com.example.ui.books

import com.example.GeneralViewTemplate
import com.example.model.Books
import com.example.ui.Endpoints
import com.example.ui.login.Session
import io.ktor.html.*
import kotlinx.html.*

class BooksTemplate(val session: Session?, val books: List<Books>) : Template<HTML> {
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
                                th(scope = ThScope.col) { +"Title" }
                                th(scope = ThScope.col) { +"Author" }
                                th(scope = ThScope.col) { +"" }
                            }
                        }
                        tbody {
                            books.forEach() {
                                tr {
                                    td { +"${it.title}" }
                                    td { +"${it.author}" }
                                    td {
                                        form(
                                            method = FormMethod.get,
                                            encType = FormEncType.multipartFormData,
                                            action = Endpoints.BOOK.url.plus("/${it.id}")
                                        ) {
                                            button(classes = "btn btn-success", type = ButtonType.submit) {
                                                +"Details"
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
