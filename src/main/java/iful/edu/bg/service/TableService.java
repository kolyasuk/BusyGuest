package iful.edu.bg.service;

import java.security.Principal;
import java.util.List;

import iful.edu.bg.enums.TableStatuses;
import iful.edu.bg.model.Establishment;
import iful.edu.bg.model.Table;

public interface TableService {

	Table createTable(Table table) throws Exception;

	Table updateTable(Table tableFromDB, Table table) throws Exception;
	
	void deleteTable(Table table) throws Exception;

	Table getTableById(String id) throws Exception;

	List<Table> getTableListByEstb(String email) throws Exception;

	Table getEstbTableById(String id) throws Exception;

	Table getEstbTableBySeats(Establishment estb, int seats);

	List<Table> getEstbTableListByStatus(Principal principal, TableStatuses status) throws Exception;

}
