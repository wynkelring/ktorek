package com.example.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBBooksTable : Table<DBBooksEntity>("books") {

    val id = int("id").primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val author = varchar("author").bindTo { it.author }
    val price = double("price").bindTo { it.price }
    val user = int("user_id").references(DBUsersTable) { it.user }
}

interface DBBooksEntity : Entity<DBBooksEntity> {

    companion object : Entity.Factory<DBBooksEntity>()

    var id: Int
    var title: String
    var author: String
    var price: Double
    var user: DBUsersEntity

}
