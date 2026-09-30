package com.sadturtleman.kuit.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.usecase.GetBookUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val bookId: Int,
    private val getBookUseCase: GetBookUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onIntent(intent: DetailIntent) {
        when (intent) {
            DetailIntent.ToggleBookmark -> toggleBookmark()
        }
    }

    private fun load() {
        viewModelScope.launch {
            getBookUseCase(bookId)
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
                .collect { book ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            book = book,
                            error = if (book == null) {
                                "책을 찾을 수 없습니다."
                            } else {
                                null
                            }
                        )
                    }
                }
        }
    }

    private fun toggleBookmark() {
        viewModelScope.launch {
            toggleBookmarkUseCase(bookId)
        }
    }
}