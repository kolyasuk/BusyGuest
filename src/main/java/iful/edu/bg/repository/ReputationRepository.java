package iful.edu.bg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.model.Reputation;

public interface ReputationRepository extends MongoRepository<Reputation, String>{

}
