package iful.edu.bg.repository;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import iful.edu.bg.model.BookedTable;

public interface BookedTableRepository extends MongoRepository<BookedTable, ObjectId> {

	@Query("{ 'table._id' : ?0, 'bookedOn': {$gt:?1}}")
	List<BookedTable> findActualBookedTables(ObjectId id, Date date);

}
