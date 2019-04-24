package iful.edu.bg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iful.edu.bg.model.BookedTable;
import iful.edu.bg.model.Establishment;
import iful.edu.bg.model.Table;
import iful.edu.bg.model.User;
import iful.edu.bg.service.BookedTableServiceImpl;
import iful.edu.bg.service.EstablishmentServiceImpl;
import iful.edu.bg.service.TableServiceImpl;

@RestController
@RequestMapping("/estb")
public class EstablishmentController {
	

	@Autowired
	EstablishmentServiceImpl establishmentServiceImpl;
	
	@Autowired
	TableServiceImpl tableServiceImpl;
	
	
	@Autowired
	BookedTableServiceImpl bookedTableServiceImpl;
	
	@GetMapping("establishment/{id}")
	public Establishment getEstablishmentById(@PathVariable("id") String id, @AuthenticationPrincipal User user) throws Exception {
		return establishmentServiceImpl.findById(id);
	}
	
	@GetMapping("establishment/{id}/table")
	public List<Table> getEstablishmentTables(@PathVariable("id") String id) throws Exception {
		return tableServiceImpl.getTableListByEstb(id);
	}
	
	@GetMapping("bookedTables/{tableId}")
	public List<BookedTable> getBookedTables(@PathVariable("tableId") String id) throws Exception {
		return bookedTableServiceImpl.getActualBookedTableByTableId(id);
	}
	
	@GetMapping("bookedTable/{id}")
	public BookedTable getBookedTable(@PathVariable("id") String id) throws Exception {
		return bookedTableServiceImpl.getBookedTableById(id);
	}

}
