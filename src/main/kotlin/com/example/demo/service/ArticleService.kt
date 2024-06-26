package com.example.demo.service

import com.example.demo.model.Article
import com.example.demo.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(private val repository: ArticleRepository) {

    fun createOrUpdateArticle(article: Article): Article {
        return repository.save(article)
    }
}
