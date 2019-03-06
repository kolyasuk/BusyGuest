package iful.edu.bg.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iful.edu.bg.dao.TableDAOImpl;
import iful.edu.bg.enums.TableStatuses;
import iful.edu.bg.model.Establishment;
import iful.edu.bg.model.Table;

@Service
public class TableServiceImpl implements TableService {

	@Autowired
	private TableDAOImpl tableDAOImpl;

	@Override
	public void createTable(Table table, Principal principal) throws Exception {
		tableDAOImpl.createTable(table, principal);
	}

	@Override
	public void updateTable(Establishment estb, Table table) {
		tableDAOImpl.updateTable(estb, table);

	}

	@Override
	public Table getTableById(long id) throws Exception {
		return tableDAOImpl.getTableById(id);
	}

	@Override
	public List<Table> getTableListByEstb(Principal principal) throws Exception {
		// TODO Auto-generated method stub
		return tableDAOImpl.getTableListByEstb(principal);
	}

	@Override
	public Table getEstbTableByNum(Establishment estb, int tableNum) {
		// TODO Auto-generated method stub
		return tableDAOImpl.getEstbTableByNum(estb, tableNum);
	}

	@Override
	public Table getEstbTableBySeats(Establishment estb, int seats) {
		// TODO Auto-generated method stub
		return tableDAOImpl.getEstbTableBySeats(estb, seats);
	}

	@Override
	public List<Table> getEstbTableListByStatus(Principal principal, TableStatuses status) throws Exception {
		// TODO Auto-generated method stub
		return tableDAOImpl.getEstbTableListByStatus(principal, status);
	}

}
