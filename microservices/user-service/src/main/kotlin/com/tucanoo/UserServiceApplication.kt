package com.tucanoo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserServiceApplication

private val log: Logger = LoggerFactory.getLogger(UserServiceApplication::class.java)

fun main(args: Array<String>) {
    runApplication<UserServiceApplication>(*args)

//    val databaseUrl = ctx.environment.getProperty("spring.datasource.url")
//    log.info("Connected to Database: $databaseUrl")

}
