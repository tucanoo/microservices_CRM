package com.tucanoo.api.core.user

import com.tucanoo.crm.enums.Role

class UserResponseDto {
    var id: Long? = null
    var role: Role? = null
    var username: String? = null
    var fullName: String? = null
    var enabled: Boolean? = false
}