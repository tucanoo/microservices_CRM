package com.tucanoo.crm.core.customer.controllers

import com.tucanoo.api.core.customer.CustomerDto
import com.tucanoo.api.core.customer.ICustomerController
import com.tucanoo.api.core.grids.GridEntityToRowMapper
import com.tucanoo.api.core.grids.GridParams
import com.tucanoo.api.core.grids.GridUtils
import com.tucanoo.api.exceptions.InvalidInputException
import com.tucanoo.api.exceptions.NotFoundException
import com.tucanoo.crm.core.customer.data.Customer
import com.tucanoo.crm.core.customer.data.CustomerRepository
import com.tucanoo.crm.core.customer.mappings.CustomerMapper
import com.tucanoo.util.ServiceUtil
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(
    private val mapper: CustomerMapper,
    private val customerRepository: CustomerRepository,
    private val gridUtils: GridUtils
) : ICustomerController {

    private val log = LoggerFactory.getLogger(CustomerController::class.java)

    /**
     * This mapper transforms a Customer entity into a map representation suitable for the data grid.
     * Each customer's information is mapped to a key-value pair where the key is the field name
     * and the value is the corresponding value for that customer.
     *
     * Additionally, the service's address is appended to each mapped customer.
     */
    private val customerDataGridMapper = GridEntityToRowMapper<Customer> { customer ->
        mapOf(
            "id" to customer.id,
            "firstName" to customer.firstName,
            "lastName" to customer.lastName,
            "emailAddress" to customer.emailAddress,
            "city" to customer.city,
            "country" to customer.country,
            "phoneNumber" to customer.phoneNumber
        )
    }

    override fun get(params: GridParams): ResponseEntity<String> {
        return ResponseEntity.ok(gridUtils.getDataForDataGrid(params, customerRepository, customerDataGridMapper))
    }

    override fun get(id: Long): ResponseEntity<CustomerDto> {
        log.debug("/customer return customer for id {}", id)

        val customer = customerRepository.findById(id).orElseThrow {
            log.warn("Customer not found for id {}", id)
            NotFoundException("Customer not found for id $id")
        }

        val response = mapper.toDto(customer)

        return ResponseEntity.ok(response)
    }

    override fun createCustomer(body: CustomerDto): ResponseEntity<CustomerDto> {
        log.debug("/createCustomer")

        val customer = mapper.toEntity(body)
        val newCustomer = customerRepository.save(customer)
        return ResponseEntity.ok(mapper.toDto(newCustomer))
    }

    @Transactional
    override fun updateCustomer(body: CustomerDto): ResponseEntity<CustomerDto> {
        log.debug("/updateCustomer")

        val customerId = body.id ?: throw InvalidInputException("Customer ID must be provided")

        val customer = customerRepository.findById(customerId)
            .orElseThrow { NotFoundException("Customer not found for id ${body.id}") }

        mapper.updateEntity(body, customer)

        val updatedCustomer = customerRepository.save(customer)

        return ResponseEntity.ok(mapper.toDto(updatedCustomer))
    }

}