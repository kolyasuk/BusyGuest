package iful.edu.bg.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iful.edu.bg.dao.BookedTableDAOImpl;
import iful.edu.bg.model.BookedTable;

@Service
public class BookedTableServiceImpl implements BookedTableService {

	@Autowired
	private BookedTableDAOImpl bookedTableDAOImpl;

	@Override
	public void createBookedTable(BookedTable bookedTable) {
		bookedTableDAOImpl.createBookedTable(bookedTable);
	}

	@Override
	public List<BookedTable> getActualBookedTableByTableNum(Principal principal, int tableNum) throws Exception {
		// TODO Auto-generated method stub
		return bookedTableDAOImpl.getActualBookedTableByTableNum(principal, tableNum);
	}

}
