package com.example.ui

enum class Endpoints(val url: String) {
    LOGIN("/login"),
    SIGNUP("/signup"),
    LOGOUT("/logout"),
    PROFILE("/profile"),
    HOME("/home"),
    BOOKS("/books"),
    BOOK("/book"),
    ADDBOOK("/book/add"),
    EDITBOOK("/book/{id}/edit"),
    DELETEBOOK("/book/{id}/delete"),
}
