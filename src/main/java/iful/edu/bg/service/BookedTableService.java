package iful.edu.bg.service;

import java.util.List;

import iful.edu.bg.entity.BookedTable;

public interface BookedTableService {

	BookedTable createBookedTable(BookedTable bookedTable);

	List<BookedTable> getActualBookedTableByTableId(String id) throws Exception;
	
	BookedTable getBookedTableById(String id) throws Exception;
	
	List<BookedTable> getBookedTableByUserId(String id) throws Exception;
	
	void deleteBookedTable(String id);
	
	boolean checkActualBookedTableByEstablishmentId(String userId, String estbId) throws Exception;

}
