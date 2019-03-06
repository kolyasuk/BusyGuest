package iful.edu.bg.service;

import java.security.Principal;
import java.util.List;

import iful.edu.bg.model.BookedTable;

public interface BookedTableService {

	void createBookedTable(BookedTable bookedTable);

	List<BookedTable> getActualBookedTableByTableNum(Principal principal, int tableNum) throws Exception;

}
