package com.sadturtleman.kuit.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sadturtleman.kuit.R

val NotoSansKR = FontFamily(
    Font(
        resId = R.font.noto_sans_kr_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.noto_sans_kr_medium,
        weight = FontWeight.Medium
    )
)

val Roboto = FontFamily(
    Font(
        resId = R.font.roboto_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.roboto_medium,
        weight = FontWeight.Medium
    )
)

val ScreenTitle = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    lineHeight = 30.8.sp,
    letterSpacing = 0.sp
)

val DetailTitle = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    lineHeight = 33.6.sp,
    letterSpacing = 0.sp
)

val BookTitle = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 22.4.sp,
    letterSpacing = 0.sp
)

val BookAuthor = TextStyle(
    fontFamily = NotoSansKR,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 19.6.sp,
    letterSpacing = 0.sp
)

val TopAction = TextStyle(
    fontFamily = NotoSansKR,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 19.6.sp,
    letterSpacing = 0.sp
)

val BookmarkButtonText = TextStyle(
    fontFamily = NotoSansKR,
    fontWeight = FontWeight.Medium,
    fontSize = 15.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp
)

val Metadata = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Normal,
    fontSize = 15.sp,
    lineHeight = 21.sp,
    letterSpacing = 0.sp
)

val DescriptionTitle = TextStyle(
    fontFamily = NotoSansKR,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 22.4.sp,
    letterSpacing = 0.sp
)

val DescriptionBody = TextStyle(
    fontFamily = NotoSansKR,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 22.4.sp,
    letterSpacing = 0.sp
)

val EmptyTitle = TextStyle(
    fontFamily = NotoSansKR,
    fontWeight = FontWeight.Medium,
    fontSize = 15.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp
)

val EmptyDescription = TextStyle(
    fontFamily = NotoSansKR,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    lineHeight = 18.2.sp,
    letterSpacing = 0.sp
)

val BookmarkCount = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 22.4.sp,
    letterSpacing = 0.sp
)

val SmallLabel = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 16.8.sp,
    letterSpacing = 0.sp
)