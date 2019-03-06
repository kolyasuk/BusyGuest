package iful.edu.bg.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.model.TableStatus;

public interface TableStatusRepository extends MongoRepository<TableStatus, ObjectId> {
	TableStatus findByStatus(String status);
}
