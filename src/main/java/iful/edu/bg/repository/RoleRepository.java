package iful.edu.bg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.enums.Roles;
import iful.edu.bg.model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

	Role findByName(Roles role);

}
