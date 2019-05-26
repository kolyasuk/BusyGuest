package iful.edu.bg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.entity.Reputation;

public interface ReputationRepository extends MongoRepository<Reputation, String>{

}
