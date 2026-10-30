package com.sadturtleman.kuit.domain

import javax.inject.Inject


class GetBookUseCase @Inject constructor(private val repo: BookRepository) {
    suspend operator fun invoke(id: String): Book = repo.getBook(id)
}

class ToggleBookmarkUseCase @Inject constructor(private val repo: BookRepository) {
     operator fun invoke(id: String) = repo.toggleBookmark(id)
}

