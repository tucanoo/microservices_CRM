package com.tucanoo.crm.core.user.init

import com.tucanoo.crm.core.user.data.User
import com.tucanoo.crm.core.user.data.UserRepository
import com.tucanoo.crm.enums.Role
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class Bootstrap(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @EventListener
    fun appReady(event: ApplicationReadyEvent) {

        // Initialise initial users
        // Create admin user.
        if (!userRepository.findByUsername("admin").isPresent) {
            val adminUser = User(
                username = "admin",
                fullName = "Sheev Palpatine",
                password = passwordEncoder.encode("admin"),
                role = Role.ADMIN,
                enabled = true
            )

            userRepository.save(adminUser)
        }

        // Create standard user.
        if (!userRepository.findByUsername("user").isPresent) {
            val adminUser = User(
                username = "user",
                fullName = "Darth Tyranus",
                password = passwordEncoder.encode("user"),
                role = Role.USER,
                enabled = true
            )

            userRepository.save(adminUser)
        }

        // create readonly user
        if (!userRepository.findByUsername("readonly_user").isPresent) {

            val adminUser = User(
                username = "readonly_user",
                fullName = "Shin Hati",
                password = passwordEncoder.encode("readonly_user"),
                role = Role.READONLY_USER,
                enabled = true
            )

            userRepository.save(adminUser)
        }
    }
}