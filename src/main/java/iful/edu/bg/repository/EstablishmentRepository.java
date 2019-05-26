package iful.edu.bg.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.entity.Establishment;

public interface EstablishmentRepository extends MongoRepository<Establishment, String> {

	Optional<Establishment> findByEmail(String email);
}
