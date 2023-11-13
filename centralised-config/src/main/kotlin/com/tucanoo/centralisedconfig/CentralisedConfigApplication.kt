package com.tucanoo.centralisedconfig

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer
import java.io.File


@EnableConfigServer
@SpringBootApplication
class CentralisedConfigApplication

private val LOG = LoggerFactory.getLogger(CentralisedConfigApplication::class.java)

fun main(args: Array<String>) {
    val ctx = runApplication<CentralisedConfigApplication>(*args)
    val configLocation = ctx.environment.getProperty("spring.cloud.config.server.native.searchLocations")
    val f = File("x.txt")

    LOG.info("Config location: $configLocation")
    LOG.info("File location: ${f.absolutePath}")
}
