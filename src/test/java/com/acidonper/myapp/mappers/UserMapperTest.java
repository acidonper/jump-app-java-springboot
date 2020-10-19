package com.acidonper.myapp.mappers;

import com.acidonper.myapp.dtos.UserDto;
import com.acidonper.myapp.entities.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void userToUserDTO() {
        User user = new User("x000","test","01");
        UserDto userDto = UserMapper.INSTANCE.userToUserDTO(user);
        assertThat(userDto.toString()).isEqualTo("User[id='x000', firstName='test', lastName='01']");
    }

    @Test
    void userDTOtoUser() {
        UserDto userDto = new UserDto("x000","test","01");
        User user = UserMapper.INSTANCE.userDTOtoUser(userDto);
        assertThat(user.toString()).isEqualTo("User[dni='x000', firstName='test', lastName='01']");
    }
}