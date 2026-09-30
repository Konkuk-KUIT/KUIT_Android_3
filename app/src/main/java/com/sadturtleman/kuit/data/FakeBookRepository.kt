package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeBookRepository : BookRepository {

    private val books = MutableStateFlow(
        listOf(
            Book(
                id = 1,
                title = "Kotlin in Action",
                author = "Dmitry Jemerov",
                pages = 360,
                year = 2017,
                description = "Kotlin 언어의 핵심 개념과 실전 활용 방법을 다루는 책입니다."
            ),
            Book(
                id = 2,
                title = "Clean Architecture",
                author = "Robert C. Martin",
                pages = 432,
                year = 2017,
                description = "소프트웨어 구조와 설계 원칙을 다루는 책. 의존성 규칙, 경계, 계층 분리에 대해 설명하며, 이번 주 세미나에서 다룬 Layered Architecture의 원전에 가깝다. 실습에서는 이 설명 텍스트를 도메인 모델의 description 필드로 추가한다."
            ),
            Book(
                id = 3,
                title = "Jetpack Compose 완벽 가이드",
                author = "KUIT",
                pages = 320,
                year = 2026,
                description = "Jetpack Compose를 활용한 Android UI 개발 방법을 다룹니다."
            ),
            Book(
                id = 4,
                title = "Refactoring UI",
                author = "Adam Wathan",
                pages = 252,
                year = 2018,
                description = "개발자의 관점에서 좋은 UI를 설계하는 방법을 설명합니다."
            ),
            Book(
                id = 5,
                title = "Effective Kotlin",
                author = "Marcin Moskała",
                pages = 360,
                year = 2020,
                description = "더 안전하고 효율적인 Kotlin 코드를 작성하기 위한 방법을 다룹니다."
            )
        )
    )

    override fun getBooks(): Flow<List<Book>> {
        return books
    }

    override fun getBook(id: Int): Flow<Book?> {
        return books.map { bookList ->
            bookList.firstOrNull { book ->
                book.id == id
            }
        }
    }

    override suspend fun toggleBookmark(id: Int) {
        books.update { bookList ->
            bookList.map { book ->
                if (book.id == id) {
                    book.copy(
                        isBookmarked = !book.isBookmarked
                    )
                } else {
                    book
                }
            }
        }
    }
}