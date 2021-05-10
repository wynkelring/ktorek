package com.example.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBUsersTable : Table<DBUsersEntity>("users") {

    val id = int("id").primaryKey().bindTo { it.id }
    val username = varchar("username").bindTo { it.username }
    val password = varchar("password").bindTo { it.password }
    val fullName = varchar("fullName").bindTo { it.fullName }
}

interface DBUsersEntity : Entity<DBUsersEntity> {

    companion object : Entity.Factory<DBUsersEntity>()

    var id: Int
    var username: String
    var password: String
    var fullName: String

}
