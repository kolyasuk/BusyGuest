package iful.edu.bg.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.model.Role;

public interface RoleRepository extends MongoRepository<Role, ObjectId> {

	Role findByRole(String role);

}
