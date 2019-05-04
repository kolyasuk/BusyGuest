package iful.edu.bg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iful.edu.bg.model.Establishment;
import iful.edu.bg.model.Table;
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
	public Establishment getEstablishmentById(@PathVariable("id") String id) throws Exception {
		return establishmentServiceImpl.findById(id);
	}
	
	@GetMapping("establishment/{estbId}/table")
	public List<Table> getEstablishmentTables(@PathVariable("estbId") String id) throws Exception {
		return tableServiceImpl.getTableListByEstb(id);
	}
	
	@GetMapping("establishment/table/{id}")
	public Table getTableById(@PathVariable("id") String id) throws Exception {
		return tableServiceImpl.getTableById(id);
	}
	
	@PostMapping("establishment/table")
	public Table createTable(@RequestBody Table table) throws Exception {
		return tableServiceImpl.createTable(table);
	}
	
	@PutMapping("establishment/table/{id}")
	public Table updateTable(@PathVariable("id") Table tableFromDB, @RequestBody Table table) throws Exception {
		return tableServiceImpl.updateTable(tableFromDB, table);
	}
	
	@PutMapping("establishment/{id}")
	public Establishment updateEstablishment(@PathVariable("id") Establishment establishmentFromDB, @RequestBody Establishment establishment) {
		return establishmentServiceImpl.updateEstablishment(establishmentFromDB, establishment);
	}
	
}
