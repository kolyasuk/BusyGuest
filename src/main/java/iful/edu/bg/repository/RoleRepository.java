package iful.edu.bg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.entity.Role;
import iful.edu.bg.enums.Roles;

public interface RoleRepository extends MongoRepository<Role, String> {

	Role findByName(Roles role);

}
