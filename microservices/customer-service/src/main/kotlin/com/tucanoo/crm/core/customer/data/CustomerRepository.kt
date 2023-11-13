package com.tucanoo.crm.core.customer.data

import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CrudRepository<Customer, Long>,
    PagingAndSortingRepository<Customer, Long>,
    JpaSpecificationExecutor<Customer>