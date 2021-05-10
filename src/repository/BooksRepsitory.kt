package com.example.repository

import com.example.database.DataManagerMySQL
import com.example.model.Books

class BooksRepository : IBooksRepository {

    private val database = DataManagerMySQL()
    override fun getAllBooks(): List<Books> {
        return database.getAllBooks()
            .map { Books(it.id, it.title, it.author, it.price.toFloat(), it.user) }
    }

    override fun getBooks(id: Int): Books? {
        return database.getBook(id)
            ?.let { Books(it.id, it.title, it.author, it.price.toFloat(), it.user) }
    }

    override fun addBooks(draft: Books) {
        database.addBook(draft)
    }

    override fun removeBooks(id: Int): Boolean {
        return database.removeBook(id)
    }

    override fun updateBooks(id: Int, draft: Books): Boolean {
        return database.updateBook(id, draft)
    }
}
