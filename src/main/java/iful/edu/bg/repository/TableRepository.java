package iful.edu.bg.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import iful.edu.bg.entity.Establishment;
import iful.edu.bg.entity.Table;
import iful.edu.bg.enums.TableStatuses;

public interface TableRepository extends MongoRepository<Table, String> {

	List<Table> findAllByEstb(Establishment estb);

	Table findByEstbAndSeats(Establishment estb, int seats);

	List<Table> findAllByEstbAndStatus(Establishment estb, TableStatuses status);

}
