package iful.edu.bg.dao;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iful.edu.bg.enums.TableStatuses;
import iful.edu.bg.model.Establishment;
import iful.edu.bg.model.Table;
import iful.edu.bg.model.TableStatus;
import iful.edu.bg.repository.TableRepository;
import iful.edu.bg.repository.TableStatusRepository;
import iful.edu.bg.service.EstablishmentServiceImpl;

@Component
public class TableDAOImpl implements TableDAO {

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private EstablishmentServiceImpl establishmentServiceImpl;

	@Autowired
	private TableStatusRepository tableStatusRepository;

	@Override
	public Table getTableById(long id) throws Exception {
		Optional<Table> table = tableRepository.findById(id);
		if (table.isPresent())
			return table.get();
		else
			throw new Exception("Not found");
	}

	@Override
	public List<Table> getTableListByEstb(Principal principal) throws Exception {
		Establishment estb = establishmentServiceImpl.findByEmail(principal.getName());
		return tableRepository.findAllByEstb(estb);
	}

	@Override
	public Table getEstbTableByNum(Establishment estb, int tableNum) {

		return tableRepository.findByEstbAndTableNum(estb, tableNum);
	}

	@Override
	public Table getEstbTableBySeats(Establishment estb, int seats) {
		return tableRepository.findByEstbAndSeats(estb, seats);
	}

	@Override
	public List<Table> getEstbTableListByStatus(Principal principal, TableStatuses status) throws Exception {
		Establishment establishment = establishmentServiceImpl.findByEmail(principal.getName());
		TableStatus ts = tableStatusRepository.findByStatus(status.toString());
		return tableRepository.findAllByEstbAndStatus(establishment, ts);
	}

	@Override
	public void createTable(Table table, Principal principal) throws Exception {
		Establishment establishment = establishmentServiceImpl.findByEmail(principal.getName());
		table.setEstb(establishment);
		TableStatus ts = tableStatusRepository.findByStatus(TableStatuses.BUSY.toString());
		table.setStatus(ts);
		tableRepository.save(table);
	}

	@Override
	public void updateTable(Establishment estb, Table table) {
		Table oldTable = getEstbTableByNum(estb, table.getTableNum());
		table.set_id(oldTable.get_id());
		tableRepository.save(table);
	}

}
