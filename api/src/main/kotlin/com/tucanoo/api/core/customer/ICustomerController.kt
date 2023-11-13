package com.tucanoo.api.core.customer

import com.tucanoo.api.core.grids.GridParams
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface ICustomerController {

    /**
     * Retrieves a paginated list of customers based on the provided grid parameters.
     *
     * @param params The grid parameters to determine pagination and sorting.
     * @return A ResponseEntity containing a JSON string representation of the paginated customer data.
     */
    @GetMapping("/customer")
    fun get(params: GridParams): ResponseEntity<String>

    /**
     * Retrieves a specific customer by their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return A ResponseEntity containing the customer data, or a NOT_FOUND status if the customer does not exist.
     */
    @GetMapping("/customer/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<CustomerDto>

    /**
     * Creates a new customer.
     *
     * @param body The customer data to create.
     * @return A ResponseEntity containing the created customer data.
     */
    @PostMapping(
        "/customer",
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun createCustomer(@RequestBody body: CustomerDto): ResponseEntity<CustomerDto>

    /**
     * Updates an existing customer.
     *
     * @param body The customer data to update.
     * @return A ResponseEntity containing the updated customer data, or a NOT_FOUND status if the customer does not exist.
     */
    @PutMapping(
        "/customer",
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun updateCustomer(@RequestBody body: CustomerDto): ResponseEntity<CustomerDto>
}