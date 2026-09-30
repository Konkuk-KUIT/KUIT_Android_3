package com.sadturtleman.kuit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.sadturtleman.kuit.bookmarks.BookmarksScreen
import com.sadturtleman.kuit.bookmarks.BookmarksViewModel
import com.sadturtleman.kuit.data.FakeBookRepository
import com.sadturtleman.kuit.detail.DetailScreen
import com.sadturtleman.kuit.detail.DetailViewModel
import com.sadturtleman.kuit.domain.usecase.GetBookmarkedBooksUseCase
import com.sadturtleman.kuit.domain.usecase.GetBooksUseCase
import com.sadturtleman.kuit.domain.usecase.GetBookUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import com.sadturtleman.kuit.home.HomeScreen
import com.sadturtleman.kuit.home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data class Detail(
    val id: Int
) : NavKey

@Serializable
data object Bookmarks : NavKey

@Composable
fun App() {

    val repository = remember {
        FakeBookRepository()
    }

    val getBooksUseCase = remember(repository) {
        GetBooksUseCase(repository)
    }

    val getBookUseCase = remember(repository) {
        GetBookUseCase(repository)
    }

    val toggleBookmarkUseCase = remember(repository) {
        ToggleBookmarkUseCase(repository)
    }

    val getBookmarkedBooksUseCase = remember(repository) {
        GetBookmarkedBooksUseCase(repository)
    }

    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {

            entry<Home> {

                val vm: HomeViewModel = viewModel {
                    HomeViewModel(
                        getBooksUseCase = getBooksUseCase,
                        toggleBookmarkUseCase = toggleBookmarkUseCase
                    )
                }

                val state by vm.state.collectAsStateWithLifecycle()

                HomeScreen(
                    state = state,
                    onIntent = vm::onIntent,
                    onBookClick = { id ->
                        backStack.add(
                            Detail(id)
                        )
                    },
                    onBookmarksClick = {
                        backStack.add(Bookmarks)
                    }
                )
            }

            entry<Detail> { key ->

                val vm: DetailViewModel = viewModel(
                    key = "detail_${key.id}"
                ) {
                    DetailViewModel(
                        bookId = key.id,
                        getBookUseCase = getBookUseCase,
                        toggleBookmarkUseCase = toggleBookmarkUseCase
                    )
                }

                val state by vm.state.collectAsStateWithLifecycle()

                DetailScreen(
                    state = state,
                    onIntent = vm::onIntent,
                    onBackClick = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<Bookmarks> {

                val vm: BookmarksViewModel = viewModel {
                    BookmarksViewModel(
                        getBookmarkedBooksUseCase = getBookmarkedBooksUseCase,
                        toggleBookmarkUseCase = toggleBookmarkUseCase
                    )
                }

                val state by vm.state.collectAsStateWithLifecycle()

                BookmarksScreen(
                    state = state,
                    onIntent = vm::onIntent,
                    onBookClick = { id ->
                        backStack.add(
                            Detail(id)
                        )
                    },
                    onBackClick = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}