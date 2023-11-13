package com.tucanoo.crm.core.user.data

import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Long>,
    PagingAndSortingRepository<User, Long>,
    JpaSpecificationExecutor<User> {
    fun findByUsername(username: String): Optional<User>
}
