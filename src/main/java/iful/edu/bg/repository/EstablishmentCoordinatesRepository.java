package iful.edu.bg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.entity.EstablishmentCoordinates;

public interface EstablishmentCoordinatesRepository extends MongoRepository<EstablishmentCoordinates, String>{

}
