package iful.edu.bg.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.model.Establishment;
import iful.edu.bg.model.Table;
import iful.edu.bg.model.TableStatus;

public interface TableRepository extends MongoRepository<Table, Long> {

	List<Table> findAllByEstb(Establishment estb);

	Table findByEstbAndTableNum(Establishment estb, int tableNum);

	Table findByEstbAndSeats(Establishment estb, int seats);

	List<Table> findAllByEstbAndStatus(Establishment estb, TableStatus status);

}
