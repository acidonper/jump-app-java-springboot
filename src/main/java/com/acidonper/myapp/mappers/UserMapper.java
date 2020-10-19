package com.acidonper.myapp.mappers;

import com.acidonper.myapp.dtos.UserDto;
import com.acidonper.myapp.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "entity.dni"),
            @Mapping(target = "firstName", source = "entity.firstName"),
            @Mapping(target = "lastName", source = "entity.lastName")
    })
    UserDto userToUserDTO(User entity);

    @Mappings({
            @Mapping(target="dni", source="dto.id"),
            @Mapping(target="firstName", source="dto.firstName"),
            @Mapping(target="lastName", source="dto.lastName")
    })
    User userDTOtoUser(UserDto dto);
}

















