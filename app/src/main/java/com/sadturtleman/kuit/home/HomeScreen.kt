package com.sadturtleman.kuit.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sadturtleman.kuit.ui.component.BookCard
import com.sadturtleman.kuit.ui.theme.BackgroundColor
import com.sadturtleman.kuit.ui.theme.BookmarkColorBackground
import com.sadturtleman.kuit.ui.theme.BookmarkColorText
import com.sadturtleman.kuit.ui.theme.BookmarkCount
import com.sadturtleman.kuit.ui.theme.CardColor
import com.sadturtleman.kuit.ui.theme.FloatingBookmarkColor
import com.sadturtleman.kuit.ui.theme.ScreenTitle
import com.sadturtleman.kuit.ui.theme.SubTextColor
import com.sadturtleman.kuit.ui.theme.TitleColor
import com.sadturtleman.kuit.ui.theme.TopAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
    onBookClick: (Int) -> Unit,
    onBookmarksClick: () -> Unit
) {
    Scaffold(
        containerColor = BackgroundColor,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "KUIT Books",
                        style = ScreenTitle,
                        color = TitleColor
                    )
                },
                actions = {
                    TextButton(
                        onClick = {
                            onIntent(HomeIntent.Refresh)
                        }
                    ) {
                        Text(
                            text = "새로고침",
                            style = TopAction,
                            color = BookmarkColorBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CardColor
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onBookmarksClick,
                containerColor = FloatingBookmarkColor,
                contentColor = BookmarkColorText,
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "★ 북마크 ${state.books.count { it.isBookmarked }}",
                        style = BookmarkCount,
                        color = BookmarkColorText
                    )
                }
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = BookmarkColorBackground
                    )
                }

                state.error != null -> {
                    Text(
                        text = "에러",
                        color = SubTextColor,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            top = 16.dp,
                            end = 16.dp,
                            bottom = 88.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = state.books,
                            key = { it.id }
                        ) { book ->
                            BookCard(
                                book = book,
                                onClick = {
                                    onBookClick(book.id)
                                },
                                onBookmarkClick = {
                                    onIntent(
                                        HomeIntent.ToggleBookmark(book.id)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
