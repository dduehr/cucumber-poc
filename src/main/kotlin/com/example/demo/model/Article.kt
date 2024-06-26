package com.example.demo.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotEmpty
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "articles")
class Article {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null

    @field:Column(nullable = false)
    @field:NotEmpty(message = "Title is empty")
    var title: String? = null

    @field:Column(nullable = false, length = 6000)
    @field:NotEmpty(message = "Body is empty")
    var body: String? = null

    @field:UpdateTimestamp
    var lastUpdatedOn: LocalDateTime? = null

    @field:CreationTimestamp
    var createdOn: LocalDateTime? = null
}