package com.tucanoo.api.core.user

import com.tucanoo.api.core.grids.GridParams
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface IUserController {

    /**
     * Retrieves a paginated list of users based on the provided grid parameters.
     *
     * @param params The grid parameters to determine pagination and sorting.
     * @return A ResponseEntity containing a JSON string representation of the paginated user data.
     */
    @GetMapping("/user")
    fun get(params: GridParams): ResponseEntity<String>

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A ResponseEntity containing the user data, or a NOT_FOUND status if the user does not exist.
     */
    @GetMapping("/user/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<UserResponseDto>

    /**
     * Creates a new user.
     *
     * @param body The user data to create.
     * @return A ResponseEntity containing the created user data.
     */
    @PostMapping(
        "/user",
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun createUser(@RequestBody body: UserDto): ResponseEntity<UserResponseDto>

    /**
     * Updates an existing user.
     *
     * @param body The user data to update.
     * @return A ResponseEntity containing the updated user data, or a NOT_FOUND status if the user does not exist.
     */
    @PutMapping(
        "/user",
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun updateUser(@RequestBody body: UserDto): ResponseEntity<UserResponseDto>

}