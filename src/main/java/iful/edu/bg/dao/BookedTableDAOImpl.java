package iful.edu.bg.dao;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iful.edu.bg.model.BookedTable;
import iful.edu.bg.model.Establishment;
import iful.edu.bg.model.Table;
import iful.edu.bg.repository.BookedTableRepository;
import iful.edu.bg.service.EstablishmentServiceImpl;

@Component
public class BookedTableDAOImpl implements BookedTableDAO {

	@Autowired
	private BookedTableRepository bookedTableRepository;

	@Autowired
	private TableDAOImpl tableDAOImpl;

	@Autowired
	private EstablishmentServiceImpl establishmentServiceImpl;

	@Override
	public void createBookedTable(BookedTable bookedTable) {
		bookedTableRepository.save(bookedTable);
	}

	@Override
	public List<BookedTable> getActualBookedTableByTableNum(Principal principal, int tableNum) throws Exception {
		Establishment estb = establishmentServiceImpl.findByEmail(principal.getName());
		Table table = tableDAOImpl.getEstbTableByNum(estb, tableNum);
		return bookedTableRepository.findActualBookedTables(table.get_id(), new Date(System.currentTimeMillis()));
	}

}
