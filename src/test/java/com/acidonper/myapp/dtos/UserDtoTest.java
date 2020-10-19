package com.acidonper.myapp.dtos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserDtoTest {

    @Test
    public void getUserDto() throws Exception {
        UserDto userDto = new UserDto("022323G","pepe", "gimenez");
        assertNotNull(userDto);
        assertThat(userDto.toString()).isEqualTo("User[id='022323G', firstName='pepe', lastName='gimenez']");
    }
}