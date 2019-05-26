package iful.edu.bg.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import iful.edu.bg.entity.BookedTable;

public interface BookedTableRepository extends MongoRepository<BookedTable, String> {

	@Query("{ 'table._id' : ?0, 'bookedOn': {$gt:?1}}")
	List<BookedTable> findActualBookedTables(String id, Date date);

	@Query("{ 'user.id' : ?0, 'bookedOn': {$gt:?1}}")
	List<BookedTable> findBookedTablesByUserId(String id, Date date);
	
	@Query("{ 'user.id' : ?0, 'estb._id' : ?1, 'bookedOn': {$gt:?2}}")
	Optional<BookedTable> findActualBookedTablesByEstablishmentId(String userId, String estbId, Date date);
	
	
}
