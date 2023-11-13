package com.tucanoo.crm.core.user.mappings

import com.tucanoo.api.core.user.UserDto
import com.tucanoo.api.core.user.UserResponseDto
import com.tucanoo.crm.core.user.data.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface UserMapper {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "version", ignore = true)
    )
    fun toEntity(dto: UserDto): User

    @Mappings(
        Mapping(target = "password", ignore = true)
    )
    fun updateEntity(dto: UserDto, @MappingTarget user: User): User

    fun toDto(entity: User): UserResponseDto
}

//    fun toDtoList(entities: List<User>): List<UserResponseDto>
