package com.example.database

import com.example.SecurityHandler
import com.example.model.Books
import com.example.model.Users
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class DataManagerMySQL {
    // config
    private val hostname = "localhost"
    private val databaseName = "ktor"
    private val username = "root"
    private val password = "root"

    // database
    private val ktormDatabase: Database

    init {
        val jdbcUrl =
            "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false&useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
        ktormDatabase = Database.connect(jdbcUrl)
    }

    fun getAllBooks(): List<DBBooksEntity> {
        return ktormDatabase.sequenceOf(DBBooksTable).toList()
    }

    fun getBook(id: Int): DBBooksEntity? {
        return return ktormDatabase.sequenceOf(DBBooksTable).firstOrNull {
            it.id eq id
        }
    }

    fun getAllUsers(): List<DBUsersEntity> {
        return ktormDatabase.sequenceOf(DBUsersTable).toList()
    }

    fun addBook(books: Books) {
        ktormDatabase.insertAndGenerateKey(DBBooksTable) {
            set(DBBooksTable.title, books.title)
            set(DBBooksTable.author, books.author)
            set(DBBooksTable.price, books.price)
            set(DBBooksTable.user, books.user.id)
        }
    }

    fun addUser(user: Users) {
        ktormDatabase.insertAndGenerateKey(DBUsersTable) {
            set(DBUsersTable.username, user.username)
            set(DBUsersTable.password, SecurityHandler.hashPassword(user.password))
            set(DBUsersTable.fullName, user.fullName)
        }
    }

    fun getUserByName(username: String): DBUsersEntity? {
        return ktormDatabase.sequenceOf(DBUsersTable).firstOrNull {
            it.username eq username
        }
    }

    fun updateUser(id: Int, user: Users): Boolean {
        val updatedRows = ktormDatabase.update(DBUsersTable) {
            set(DBUsersTable.username, user.username)
            set(DBUsersTable.password, user.password)
            set(DBUsersTable.fullName, user.fullName)
            where {
                it.id eq id
            }
        }

        return updatedRows > 0
    }

    fun updateBook(id: Int, draft: Books): Boolean {
        val updatedRows = ktormDatabase.update(DBBooksTable) {
            set(DBBooksTable.title, draft.title)
            set(DBBooksTable.author, draft.author)
            set(DBBooksTable.price, draft.price)
            where {
                it.id eq id
            }
        }

        return updatedRows > 0
    }

    fun removeBook(id: Int): Boolean {
        val deletedRows = ktormDatabase.delete(DBBooksTable) {
            it.id eq id
        }

        return deletedRows > 0
    }
}
