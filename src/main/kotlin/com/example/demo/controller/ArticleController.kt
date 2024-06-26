package com.example.demo.controller

import com.example.demo.model.ArticleMapper
import com.example.demo.dto.ArticleResponseDto
import com.example.demo.service.ArticleService
import com.example.demo.dto.CreateArticleRequestDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/articles")
class ArticleController(private val service: ArticleService) {

    @PostMapping
    fun createArticle(@RequestBody payload: CreateArticleRequestDto): ArticleResponseDto {
        return service.createOrUpdateArticle(ArticleMapper.fromRequest(payload))
            .let(ArticleMapper::toResponse)
    }
}
