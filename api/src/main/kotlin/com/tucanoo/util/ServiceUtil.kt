package com.tucanoo.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.InetAddress
import java.net.UnknownHostException


@Component
class ServiceUtil(
    @Value("\${server.port}") private val port: String
) {

    var serviceAddress: String? = null
        get() {
            if (field == null) {
                field = findMyHostname() + "/" + findMyIpAddress() + ":" + port
            }
            return field
        }

    private fun findMyHostname(): String {
        return try {
            InetAddress.getLocalHost().hostName
        } catch (e: UnknownHostException) {
            "unknown host name"
        }
    }

    private fun findMyIpAddress(): String {
        return try {
            InetAddress.getLocalHost().hostAddress
        } catch (e: UnknownHostException) {
            "unknown IP address"
        }
    }
}