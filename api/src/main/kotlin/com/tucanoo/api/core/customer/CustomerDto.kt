package com.tucanoo.api.core.customer

// Represents the customer data passed from the client
class CustomerDto {
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var emailAddress: String? = null
    var address: String? = null
    var city: String? = null
    var country: String? = null
    var phoneNumber: String? = null
    var serviceAddress: String? = null
}