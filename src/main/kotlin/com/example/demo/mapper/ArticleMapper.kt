package com.example.demo.mapper

import com.example.demo.dto.ArticleResponseDto
import com.example.demo.dto.CreateArticleRequestDto
import com.example.demo.model.Article

object ArticleMapper {
  fun fromRequest(dto: CreateArticleRequestDto): Article {
    val article = Article()
    return with(article) {
      title = dto.title
      body = dto.body
      this
    }
  }

  fun toResponse(entity: Article): ArticleResponseDto {
    return ArticleResponseDto(
      id = entity.id!!,
      title = entity.title!!,
      body = entity.body!!,
      lastUpdatedOn = entity.lastUpdatedOn!!,
      createdOn = entity.createdOn!!
    )
  }
}