package com.acidonper.myapp.entities.repositories;

import java.util.List;
import com.acidonper.myapp.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    public List<User> findByDni(String dni);
}