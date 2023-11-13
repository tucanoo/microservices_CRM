package com.tucanoo.api.core.user

import com.tucanoo.crm.enums.Role

// Represents the user data passed from the client
class UserDto {
    var id: Long? = null
    var role: Role? = null
    var username: String? = null
    var fullName: String? = null
    var password: String? = null
    var enabled: Boolean? = false
}