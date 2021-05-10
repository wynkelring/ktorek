package com.example.model

import com.example.database.DBUsersEntity


data class Books(
    var id: Int?,
    var title: String,
    var author: String,
    var price: Float = 0.00f,
    var user: DBUsersEntity
)
