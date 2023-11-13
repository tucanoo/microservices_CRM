package com.tucanoo.crm.core.customer.mappings

import com.tucanoo.api.core.customer.CustomerDto
import com.tucanoo.crm.core.customer.data.Customer
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface CustomerMapper {

    @Mappings(
        Mapping(target = "id", ignore = true),
    )
    fun toEntity(dto: CustomerDto): Customer

    fun updateEntity(dto: CustomerDto, @MappingTarget customer: Customer): Customer

    fun toDto(entity: Customer): CustomerDto
}