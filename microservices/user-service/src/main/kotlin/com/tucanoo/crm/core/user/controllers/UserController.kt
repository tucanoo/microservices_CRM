package com.tucanoo.crm.core.user.controllers

import com.tucanoo.api.core.grids.GridEntityToRowMapper
import com.tucanoo.api.core.grids.GridParams
import com.tucanoo.api.core.grids.GridUtils
import com.tucanoo.api.core.user.UserDto
import com.tucanoo.api.core.user.UserResponseDto
import com.tucanoo.api.core.user.IUserController
import com.tucanoo.api.exceptions.InvalidInputException
import com.tucanoo.api.exceptions.NotFoundException
import com.tucanoo.crm.core.user.data.User
import com.tucanoo.crm.core.user.data.UserRepository
import com.tucanoo.crm.core.user.mappings.UserMapper
import com.tucanoo.util.ServiceUtil
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val serviceUtil: ServiceUtil,
    private val mapper: UserMapper,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val gridUtils: GridUtils
) : IUserController {

    private val log = LoggerFactory.getLogger(UserController::class.java)

    /**
     * This mapper transforms a User entity into a map representation suitable for the data grid.
     * Each user's information is mapped to a key-value pair where the key is the field name
     * and the value is the corresponding value for that user.
     *
     * Additionally, the service's address is appended to each mapped user.
     */
    private val userDataGridMapper = GridEntityToRowMapper<User> { user ->
        mapOf(
            "id" to user.id,
            "username" to user.username,
            "fullName" to user.fullName,
            "enabled" to user.enabled,
            "dateCreated" to user.dateCreated,
            "serviceAddress" to serviceUtil.serviceAddress
        )
    }

    override fun get(params: GridParams): ResponseEntity<String> {
        return ResponseEntity.ok(gridUtils.getDataForDataGrid(params, userRepository, userDataGridMapper))
    }

    override fun get(id: Long): ResponseEntity<UserResponseDto> {
        log.debug("/user return user for id {}", id)

        val user = userRepository.findById(id).orElseThrow {
            log.warn("User not found for id {}", id)
            NotFoundException("User not found for id $id")
        }

        val response = mapper.toDto(user)

        return ResponseEntity.ok(response)
    }

    override fun createUser(body: UserDto): ResponseEntity<UserResponseDto> {
        log.debug("/createUser")

        val user = mapper.toEntity(body)
        user.password = passwordEncoder.encode(body.password)
        user.enabled = true

        val newUser = userRepository.save(user)
        return ResponseEntity.ok(mapper.toDto(newUser))
    }

    @Transactional
    override fun updateUser(body: UserDto): ResponseEntity<UserResponseDto> {
        log.debug("/updateUser")

        val userId = body.id ?: throw InvalidInputException("User ID must be provided")

        val user = userRepository.findById(userId)
            .orElseThrow({ NotFoundException("User not found for id ${body.id}") })

        mapper.updateEntity(body, user)

        val updatedUser = userRepository.save(user)

        return ResponseEntity.ok(mapper.toDto(updatedUser))
    }

}