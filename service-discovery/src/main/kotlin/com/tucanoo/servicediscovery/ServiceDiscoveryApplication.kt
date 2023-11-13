package com.tucanoo.servicediscovery

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class ServiceDiscoveryApplication

@Value("\${spring.profiles.active}")
private val activeProfile: String? = null

fun main(args: Array<String>) {
    if (activeProfile != null) {
        for (profileName in activeProfile.split(",")) {
            println("Currently active profile - $profileName")
        }
    }
    runApplication<ServiceDiscoveryApplication>(*args)
}
