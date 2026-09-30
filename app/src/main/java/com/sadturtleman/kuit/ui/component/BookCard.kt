package com.sadturtleman.kuit.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.ui.theme.BookAuthor
import com.sadturtleman.kuit.ui.theme.BookTitle
import com.sadturtleman.kuit.ui.theme.BookmarkColorBackground
import com.sadturtleman.kuit.ui.theme.BookmarkGrayColor
import com.sadturtleman.kuit.ui.theme.CardColor
import com.sadturtleman.kuit.ui.theme.SubTextColor
import com.sadturtleman.kuit.ui.theme.TitleColor

@Composable
fun BookCard(
    book: Book,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 19.dp,
                    bottom = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = book.title,
                    style = BookTitle,
                    color = TitleColor
                )

                Text(
                    text = book.author,
                    style = BookAuthor,
                    color = SubTextColor
                )
            }

            Text(
                text = if (book.isBookmarked) "★" else "☆",
                fontSize = 25.sp,
                color = if (book.isBookmarked) {
                    BookmarkColorBackground
                } else {
                    BookmarkGrayColor
                },
                modifier = Modifier.clickable {
                    onBookmarkClick()
                }
            )
        }
    }
}