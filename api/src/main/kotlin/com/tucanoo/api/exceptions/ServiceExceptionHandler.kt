package com.tucanoo.api.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ServiceExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<Any> {
        log.error("Resource not found", e)
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf("error" to e.message))
    }

    @ExceptionHandler(InvalidInputException::class)
    fun handleInvalidInputException(e: InvalidInputException): ResponseEntity<Any> {
        log.error("An InvalidInputException occurred: ", e)
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(mapOf("error" to e.message))
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception): ResponseEntity<Any> {
        log.error("An unhandled error occurred", e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(mapOf("error" to "An unexpected error occurred"))
    }
}