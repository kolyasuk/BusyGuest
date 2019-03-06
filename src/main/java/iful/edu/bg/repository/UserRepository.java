package iful.edu.bg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
