package com.sadturtleman.kuit.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sadturtleman.kuit.ui.theme.BackgroundColor
import com.sadturtleman.kuit.ui.theme.BookmarkButtonText
import com.sadturtleman.kuit.ui.theme.BookmarkColorBackground
import com.sadturtleman.kuit.ui.theme.BookmarkColorText
import com.sadturtleman.kuit.ui.theme.CardColor
import com.sadturtleman.kuit.ui.theme.DescriptionBody
import com.sadturtleman.kuit.ui.theme.DescriptionTitle
import com.sadturtleman.kuit.ui.theme.DetailTitle
import com.sadturtleman.kuit.ui.theme.EmptyDescription
import com.sadturtleman.kuit.ui.theme.EmptyTitle
import com.sadturtleman.kuit.ui.theme.Metadata
import com.sadturtleman.kuit.ui.theme.ScreenTitle
import com.sadturtleman.kuit.ui.theme.SubTextColor
import com.sadturtleman.kuit.ui.theme.TitleColor
import com.sadturtleman.kuit.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    state: DetailUiState, onIntent: (DetailIntent) -> Unit, onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = BackgroundColor, topBar = {
            TopAppBar(
                navigationIcon = {
                Box(
                    modifier = Modifier.padding(start = 20.dp, end = 15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "←",
                        fontSize = 26.sp,
                        color = TitleColor,
                        modifier = Modifier.clickable {
                            onBackClick()
                        }.padding(bottom = 8.dp))
                }
            }, title = {
                Text(
                    text = "책 상세", style = ScreenTitle, color = TitleColor
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

                state.book != null -> {
                    val book = state.book

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(
                                start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp
                            )
                    ) {
                        Text(
                            text = book.title, style = DetailTitle, color = TitleColor
                        )

                        Text(
                            text = book.author,
                            style = Metadata,
                            color = SubTextColor,
                            modifier = Modifier.padding(top = 9.dp)
                        )

                        Text(
                            text = "${book.pages}p · ${book.year}",
                            style = EmptyDescription,
                            color = SubTextColor,
                            modifier = Modifier.padding(top = 5.dp)
                        )

                        TextButton(
                            onClick = {
                                onIntent(DetailIntent.ToggleBookmark)
                            },
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = BookmarkColorBackground
                            ),
                            shape = RoundedCornerShape(24.dp),
                            contentPadding = PaddingValues(
                                horizontal = 24.dp,
                                vertical = 12.dp
                            ),
                            modifier = Modifier.padding(top = 20.dp)
                        ) {
                            Text(
                                text = if (book.isBookmarked) {
                                    "★  북마크에서 제거"
                                } else {
                                    "☆  북마크에 추가"
                                },
                                style = BookmarkButtonText,
                                color = white
                            )
                        }

                        Text(
                            text = "소개",
                            style = DescriptionTitle,
                            color = TitleColor,
                            modifier = Modifier.padding(top = 34.dp)
                        )

                        Text(
                            text = book.description,
                            style = DescriptionBody,
                            color = SubTextColor,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}