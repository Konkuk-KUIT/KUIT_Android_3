package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.domain.Book
import com.sadturtleman.kuit.domain.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val api: BookApi,
) : BookRepository {

    private val bookmarkedIds = MutableStateFlow<Set<String>>(emptySet())
    private var lastFetched: List<VolumeDto> = emptyList()   // 북마크 화면용 캐시

    override fun observeBooks(query: String): Flow<List<Book>>{
        val remote: Flow<List<VolumeDto>> = flow{
            emit(api.searchBooks(query).items)
        }.onEach { items ->
            lastFetched = items
        }
        return combine(remote, bookmarkedIds){
            items,ids -> items.map { it.toBook(isBookmarked = it.id in ids) }
        }
    }

    override fun observeBookmarkedBooks(): Flow<List<Book>> =
        bookmarkedIds.map{
        ids-> lastFetched.filter { it.id in ids }.map { it.toBook(isBookmarked = true) }
    }

    override suspend fun getBook(id: String): Book =
        api.getBook(id).toBook(isBookmarked = id in bookmarkedIds.value)

    override fun toggleBookmark(id: String) {
        bookmarkedIds.update { ids -> if (id in ids) ids - id else ids + id }
    }


}
