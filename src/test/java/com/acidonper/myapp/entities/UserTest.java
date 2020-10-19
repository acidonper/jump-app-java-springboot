package com.acidonper.myapp.entities;

import com.acidonper.myapp.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserTest {

    @Test
    public void getUserDto() throws Exception {
        User user = new User("033212F","pepe", "gimenez");
        assertNotNull(user);
        assertThat(user.toString()).isEqualTo("User[dni='033212F', firstName='pepe', lastName='gimenez']");
    }
}