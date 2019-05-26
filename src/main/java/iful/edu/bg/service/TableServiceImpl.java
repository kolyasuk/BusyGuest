package iful.edu.bg.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iful.edu.bg.entity.Establishment;
import iful.edu.bg.entity.Table;
import iful.edu.bg.enums.TableStatuses;
import iful.edu.bg.repository.TableRepository;

@Service
public class TableServiceImpl implements TableService {

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private EstablishmentServiceImpl establishmentServiceImpl;

	@Override
	public Table getTableById(String id) throws Exception {
		Optional<Table> table = tableRepository.findById(id);
		if (table.isPresent())
			return table.get();
		else
			throw new Exception("Not found");
	}

	@Override
	public List<Table> getTableListByEstb(String id) throws Exception {
		Establishment estb = establishmentServiceImpl.findById(id);
		return tableRepository.findAllByEstb(estb);
	}

	@Override
	public Table getEstbTableById(String id) throws Exception {
		Optional<Table> table = tableRepository.findById(id);
		if(table.isPresent())
			return table.get();
		else throw new Exception("Not found");
	}

	@Override
	public Table getEstbTableBySeats(Establishment estb, int seats) {
		return tableRepository.findByEstbAndSeats(estb, seats);
	}

	@Override
	public List<Table> getEstbTableListByStatus(Principal principal, TableStatuses status) throws Exception {
		Establishment establishment = establishmentServiceImpl.findById(principal.getName());
		return tableRepository.findAllByEstbAndStatus(establishment, status);
	}

	@Override
	public Table createTable(Table table) throws Exception {
		table.setStatus(TableStatuses.FREE);
		return tableRepository.save(table);
	}

	@Override
	public Table updateTable(Table tableFromDB, Table table) throws Exception {
		BeanUtils.copyProperties(table, tableFromDB, "id"); 
		return tableRepository.save(tableFromDB);
	}

	@Override
	public void deleteTable(Table table) throws Exception {
		tableRepository.delete(table);
	}

}
