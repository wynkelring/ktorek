package com.example.repository

import com.example.database.DBUsersEntity
import com.example.model.Users

interface IUsersRepository {
    fun getAllUsers(): List<Users>

    fun getUsers(id: Int): Users?

    fun getUserByUsername(username: String): Users?

    fun getUserEntityByUsername(username: String): DBUsersEntity?

    fun addUsers(draft: Users)

    fun removeUsers(id: Int): Boolean

    fun updateUsers(id: Int, user: Users): Boolean
}
