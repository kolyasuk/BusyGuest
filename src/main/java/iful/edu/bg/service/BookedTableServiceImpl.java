package iful.edu.bg.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iful.edu.bg.entity.BookedTable;
import iful.edu.bg.entity.Table;
import iful.edu.bg.repository.BookedTableRepository;

@Service
public class BookedTableServiceImpl implements BookedTableService {

	@Autowired
	private BookedTableRepository bookedTableRepository;

	@Autowired
	private TableServiceImpl tableServiceImpl;

	@Autowired
	private EstablishmentServiceImpl establishmentServiceImpl;

	@Override
	public BookedTable createBookedTable(BookedTable bookedTable) {
		return bookedTableRepository.save(bookedTable);
	}

	@Override
	public List<BookedTable> getActualBookedTableByTableId(String id) throws Exception {
		Table table = tableServiceImpl.getEstbTableById(id);
		return bookedTableRepository.findActualBookedTables(table.get_id(), new Date(System.currentTimeMillis()));
	}

	@Override
	public BookedTable getBookedTableById(String id) throws Exception {
		Optional<BookedTable> bookedTable = bookedTableRepository.findById(id);
		if(bookedTable.isPresent())
			return bookedTable.get();
		else throw new Exception("Not found");
	}

	@Override
	public List<BookedTable> getBookedTableByUserId(String id) throws Exception {
		// TODO Auto-generated method stub
		return bookedTableRepository.findBookedTablesByUserId(id, new Date(System.currentTimeMillis()));
	}

	@Override
	public void deleteBookedTable(String id) {
		bookedTableRepository.deleteById(id);
	}

	@Override
	public boolean checkActualBookedTableByEstablishmentId(String userId, String estbId) throws Exception {
		Optional<BookedTable> bt = bookedTableRepository.findActualBookedTablesByEstablishmentId(userId, estbId, new Date(System.currentTimeMillis()));
		return bt.isPresent();
	}

}
