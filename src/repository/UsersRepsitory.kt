package com.example.repository

import com.example.database.DBUsersEntity
import com.example.database.DataManagerMySQL
import com.example.model.Users

class UsersRepository : IUsersRepository {

    private val database = DataManagerMySQL()
    override fun getAllUsers(): List<Users> {
        TODO("Not yet implemented")
    }

    override fun getUsers(id: Int): Users? {
        TODO("Not yet implemented")
    }

    override fun getUserByUsername(username: String): Users? {
        return database.getUserByName(username)
            ?.let { Users(it.id, it.username, it.password, it.fullName) }
    }

    override fun getUserEntityByUsername(username: String): DBUsersEntity? {
        return database.getUserByName(username)
    }

    override fun addUsers(draft: Users) {
        database.addUser(draft)
    }

    override fun removeUsers(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateUsers(id: Int, user: Users): Boolean {
        return database.updateUser(id, user)
    }
}
