package com.sadturtleman.kuit.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.BookRepository
import com.sadturtleman.kuit.domain.ToggleBookmarkUseCase
import com.sadturtleman.kuit.presentation.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: BookRepository,
    private val toggleBookmark: ToggleBookmarkUseCase,
) : ViewModel() {

    private val query = MutableStateFlow("android")

    // 새로고침 / 다시 시도 신호. 값이 바뀔 때마다 같은 검색을 다시 한다.
    private val refreshTrigger= MutableStateFlow(0)

    val state: StateFlow<HomeUiState> =
        combine(query, refreshTrigger) { q, _ -> q }
            .flatMapLatest { q ->
                repo.observeBooks(q)
                    .map { HomeUiState(books = it, query = q) }
                    .catch { e -> emit(HomeUiState(error = e.toUserMessage(), query = q)) }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                HomeUiState(isLoading = true),
            )

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Refresh -> refreshTrigger.update { it+1 }
            is HomeIntent.Search -> {
                if (intent.query.isBlank()) return      // 빈 검색어는 요청하지 않는다
                query.value=intent.query
            }
            is HomeIntent.ToggleBookmark -> toggle(intent.id)
        }
    }

    // Repository 가 상태의 주인이므로 다시 읽어서 새 UiState 로. (로딩 표시 없이 조용히 갱신)
    private fun toggle(id: String) {
        toggleBookmark(id)

    }
}
