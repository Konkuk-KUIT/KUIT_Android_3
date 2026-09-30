package com.sadturtleman.kuit.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.usecase.GetBooksUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getBooksUseCase: GetBooksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private var loadJob: Job? = null

    init {
        onIntent(HomeIntent.Refresh)
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Refresh -> load()

            is HomeIntent.ToggleBookmark -> {
                toggleBookmark(intent.id)
            }
        }
    }

    private fun load() {
        loadJob?.cancel()

        loadJob = viewModelScope.launch {
            getBooksUseCase()
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