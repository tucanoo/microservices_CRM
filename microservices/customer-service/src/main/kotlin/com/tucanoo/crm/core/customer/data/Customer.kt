package com.tucanoo.crm.core.customer.data

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
class Customer {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @NotBlank
    var firstName: String? = null

    @NotBlank
    var lastName: String? = null

    @Email
    var emailAddress: String? = null

    var address: String? = null

    var city: String? = null

    var country: String? = null

    var phoneNumber: String? = null

}