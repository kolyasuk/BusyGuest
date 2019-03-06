package iful.edu.bg.dao;

import java.security.Principal;
import java.util.List;

import iful.edu.bg.enums.TableStatuses;
import iful.edu.bg.model.Establishment;
import iful.edu.bg.model.Table;

public interface TableDAO {

	void createTable(Table table, Principal principal) throws Exception;

	void updateTable(Establishment estb, Table table);

	Table getTableById(long id) throws Exception;

	List<Table> getTableListByEstb(Principal principa) throws Exception;

	Table getEstbTableByNum(Establishment estb, int tableNum);

	Table getEstbTableBySeats(Establishment estb, int seats);

	List<Table> getEstbTableListByStatus(Principal principal, TableStatuses status) throws Exception;

}
