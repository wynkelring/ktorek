package com.example.repository

import com.example.model.Books

interface IBooksRepository {
    fun getAllBooks(): List<Books>

    fun getBooks(id: Int): Books?

    fun addBooks(draft: Books)

    fun removeBooks(id: Int): Boolean

    fun updateBooks(id: Int, draft: Books): Boolean
}
