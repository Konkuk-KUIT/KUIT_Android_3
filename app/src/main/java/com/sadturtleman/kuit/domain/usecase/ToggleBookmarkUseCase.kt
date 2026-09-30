package com.sadturtleman.kuit.domain.usecase

import com.sadturtleman.kuit.domain.repository.BookRepository

class ToggleBookmarkUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.toggleBookmark(id)
    }
}