package com.sadturtleman.kuit.bookmarks

import com.sadturtleman.kuit.domain.model.Book

data class BookmarksUiState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val isEmpty: Boolean = false,
    val error: String? = null
)

sealed interface BookmarksIntent {

    data class ToggleBookmark(
        val id: Int
    ) : BookmarksIntent
}