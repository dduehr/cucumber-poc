package com.example.demo.dto

import java.time.LocalDateTime

data class ArticleResponseDto(
    val id: String,
    val title: String,
    val body: String,
    val lastUpdatedOn: LocalDateTime,
    val createdOn: LocalDateTime
)
