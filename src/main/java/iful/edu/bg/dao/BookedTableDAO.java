package iful.edu.bg.dao;

import java.security.Principal;
import java.util.List;

import iful.edu.bg.model.BookedTable;

public interface BookedTableDAO {

	void createBookedTable(BookedTable bookedTable);

	List<BookedTable> getActualBookedTableByTableNum(Principal principal, int tableNum) throws Exception;

}
