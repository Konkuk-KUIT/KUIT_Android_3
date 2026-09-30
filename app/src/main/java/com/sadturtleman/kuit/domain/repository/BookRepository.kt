package com.sadturtleman.kuit.domain.repository

import com.sadturtleman.kuit.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBooks(): Flow<List<Book>>

    fun getBook(id: Int): Flow<Book?>

    suspend fun toggleBookmark(id: Int)
}