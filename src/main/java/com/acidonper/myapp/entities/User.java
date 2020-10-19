package com.acidonper.myapp.entities;

import org.springframework.data.mongodb.core.index.Indexed;
import javax.validation.constraints.NotEmpty;

public class User  {
    @NotEmpty
    public String firstName;
    @NotEmpty
    public String lastName;
    @Indexed(unique = true)
    public String dni;

    public User() {}

    public User(String dni, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
    }

    @Override
    public String toString() {
        return String.format(
                "User[dni='%s', firstName='%s', lastName='%s']",dni, firstName, lastName);
    }

}

