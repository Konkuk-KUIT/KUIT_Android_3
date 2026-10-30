package com.sadturtleman.kuit.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.BookRepository
import com.sadturtleman.kuit.domain.ToggleBookmarkUseCase
import com.sadturtleman.kuit.presentation.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val repo: BookRepository,
    private val toggleBookmark: ToggleBookmarkUseCase,
) : ViewModel() {

    val state: StateFlow<BookmarksUiState> =
        repo.observeBookmarkedBooks()
            .map { BookmarksUiState(books = it) }
            .catch { e -> emit(BookmarksUiState(error = e.toUserMessage())) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                BookmarksUiState(isLoading = true),
            )
    fun onIntent(intent: BookmarksIntent) {
        when (intent) {
            is BookmarksIntent.ToggleBookmark -> toggleBookmark(intent.id) // 해제한 책은 목록에서 바로 사라진다
            }
        }
}



