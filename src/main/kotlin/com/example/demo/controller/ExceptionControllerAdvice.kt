package com.example.demo.controller

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.TransactionSystemException
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleTransactionSystemException(ex: TransactionSystemException): ResponseEntity<ErrorResponse> =
        when (ex.rootCause) {
            is ConstraintViolationException -> handleConstraintViolationException(
                ex.rootCause as ConstraintViolationException)
            else -> handleException(ex)
        }

    @ExceptionHandler
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<ErrorResponse> =
        ResponseEntity.badRequest().body(
            ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.message ?: "Constraint violation"))

    @ExceptionHandler
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> =
        ResponseEntity.internalServerError().body(
            ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.message ?: "Internal Server Error"))
}