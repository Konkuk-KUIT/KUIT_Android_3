package com.sadturtleman.kuit.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.usecase.GetBookmarkedBooksUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookmarksViewModel(
    private val getBookmarkedBooksUseCase: GetBookmarkedBooksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookmarksUiState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onIntent(intent: BookmarksIntent) {
        when (intent) {
            is BookmarksIntent.ToggleBookmark -> {
                toggleBookmark(intent.id)
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            getBookmarkedBooksUseCase()
                .onStart {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                }
                .catch {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "에러"
                        )
                    }
                }
                .collect { books ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            books = books,
                            isEmpty = books.isEmpty(),
                            error = null
                        )
                    }
                }
        }
    }

    private fun toggleBookmark(id: Int) {
        viewModelScope.launch {
            toggleBookmarkUseCase(id)
        }
    }
}