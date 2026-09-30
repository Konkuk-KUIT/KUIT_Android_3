package com.sadturtleman.kuit.bookmarks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sadturtleman.kuit.ui.component.BookCard
import com.sadturtleman.kuit.ui.theme.BackgroundColor
import com.sadturtleman.kuit.ui.theme.BookmarkColorText
import com.sadturtleman.kuit.ui.theme.BookmarkGrayColor
import com.sadturtleman.kuit.ui.theme.CardColor
import com.sadturtleman.kuit.ui.theme.DescriptionBody
import com.sadturtleman.kuit.ui.theme.EmptyTitle
import com.sadturtleman.kuit.ui.theme.ScreenTitle
import com.sadturtleman.kuit.ui.theme.SubTextColor
import com.sadturtleman.kuit.ui.theme.TitleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    state: BookmarksUiState,
    onIntent: (BookmarksIntent) -> Unit,
    onBookClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = BackgroundColor, topBar = {
            TopAppBar(
                navigationIcon = {
                Box(
                    modifier = Modifier.padding(
                        start = 20.dp, end = 15.dp
                    ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "←",
                        fontSize = 26.sp,
                        color = TitleColor,
                        modifier = Modifier
                            .clickable {
                                onBackClick()
                            }
                            .padding(bottom = 8.dp))
                }
            }, title = {
                Text(
                    text = "북마크", style = ScreenTitle, color = TitleColor
                )
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = CardColor
            )
            )
        }) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center), color = BookmarkColorText
                    )
                }

                state.error != null -> {
                    Text(
                        text = state.error,
                        color = SubTextColor,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.isEmpty -> {
                    EmptyBookmarks(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(
                            start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp
                        ), verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = state.books, key = { it.id }) { book ->

                            BookCard(book = book, onClick = {
                                onBookClick(book.id)
                            }, onBookmarkClick = {
                                onIntent(
                                    BookmarksIntent.ToggleBookmark(book.id)
                                )
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyBookmarks(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "☆", fontSize = 63.sp, color = BookmarkGrayColor
        )

        Text(
            text = "아직 북마크한 책이 없어요",
            style = EmptyTitle,
            color = SubTextColor,
            modifier = Modifier.padding(top = 6.dp)
        )

        Text(
            text = "목록에서 ★를 눌러 저장해 보세요",
            style = DescriptionBody,
            color = BookmarkGrayColor,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}