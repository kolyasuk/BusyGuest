package iful.edu.bg.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.enums.TableStatuses;
import iful.edu.bg.model.Establishment;
import iful.edu.bg.model.Table;

public interface TableRepository extends MongoRepository<Table, String> {

	List<Table> findAllByEstb(Establishment estb);

	Table findByEstbAndSeats(Establishment estb, int seats);

	List<Table> findAllByEstbAndStatus(Establishment estb, TableStatuses status);

}
