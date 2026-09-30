package com.sadturtleman.kuit.domain.usecase

import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class GetBookUseCase(
    private val repository: BookRepository
) {
    operator fun invoke(id: Int): Flow<Book?> {
        return repository.getBook(id)
    }
}