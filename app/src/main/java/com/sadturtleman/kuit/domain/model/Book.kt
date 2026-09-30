package com.sadturtleman.kuit.domain.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val isBookmarked: Boolean = false,
    val pages: Int,
    val year: Int,
    val description: String
)