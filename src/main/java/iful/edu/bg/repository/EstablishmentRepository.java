package iful.edu.bg.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.model.Establishment;

public interface EstablishmentRepository extends MongoRepository<Establishment, Long> {

	Optional<Establishment> findByEmail(String email);
}
