package com.acidonper.myapp.dtos;

import javax.validation.constraints.NotEmpty;

public class UserDto {
    @NotEmpty
    public String firstName;
    @NotEmpty
    public String lastName;
    @NotEmpty
    public String id;

    public UserDto() {
        super();
    }

    public UserDto(String id, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id='%s', firstName='%s', lastName='%s']", id, firstName, lastName);
    }
}
