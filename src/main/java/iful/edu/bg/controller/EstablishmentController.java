package iful.edu.bg.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iful.edu.bg.entity.Establishment;
import iful.edu.bg.entity.EstablishmentCoordinates;
import iful.edu.bg.entity.Table;
import iful.edu.bg.repository.EstablishmentCoordinatesRepository;
import iful.edu.bg.service.BookedTableServiceImpl;
import iful.edu.bg.service.EstablishmentServiceImpl;
import iful.edu.bg.service.TableServiceImpl;
import javassist.NotFoundException;

@RestController
@RequestMapping("/estb")
public class EstablishmentController {
	
	@Autowired
	private EstablishmentServiceImpl establishmentServiceImpl;
	
	@Autowired
	private TableServiceImpl tableServiceImpl;
	
	@Autowired
	private BookedTableServiceImpl bookedTableServiceImpl;
	@Autowired
	private EstablishmentCoordinatesRepository establishmentCoordinatesRepository;
	
	@GetMapping("establishment/{id}")
	public Establishment getEstablishmentById(@PathVariable("id") String id) throws Exception {
		return establishmentServiceImpl.findById(id);
	}
	
	@GetMapping("establishment")
	public List<Establishment> getAllEstablishment() throws Exception {
		return establishmentServiceImpl.getEstablishmentList();
	}
	
	@PostMapping("establishment")
	public Establishment createEstablishment( @RequestBody Establishment establishment) throws Exception {
		return establishmentServiceImpl.createEstablishment(establishment);
	}
	
	@PutMapping("establishment/{id}")
	public Establishment updateEstablishment(@PathVariable("id") Establishment establishmentFromDB, @RequestBody Establishment establishment) {
		return establishmentServiceImpl.updateEstablishment(establishmentFromDB, establishment);
	}
	
	@GetMapping("establishment/{estbId}/table")
	public List<Table> getEstablishmentTables(@PathVariable("estbId") String id) throws Exception {
		return tableServiceImpl.getTableListByEstb(id);
	}
	
	@GetMapping("establishment/table/{id}")
	public Table getTableById(@PathVariable("id") String id) throws Exception {
		return tableServiceImpl.getTableById(id);
	}
	
	@GetMapping("establishment/{id}/coordinates")
	public EstablishmentCoordinates getCoordinates(@PathVariable("id") String id) throws Exception {
		Optional<EstablishmentCoordinates> ec = establishmentCoordinatesRepository.findById(id);
			if(ec.isPresent())
				return ec.get();
			else throw new NotFoundException("Not Found");
	}
	
	@PostMapping("establishment/{id}/coordinates")
	public EstablishmentCoordinates setCoordinates(@PathVariable("id") String id, @Valid@RequestBody EstablishmentCoordinates establishmentCoordinates) throws Exception {
		return establishmentCoordinatesRepository.save(establishmentCoordinates);
	}
	
	@PostMapping("establishment/table")
	public Table createTable(@RequestBody Table table) throws Exception {
		return tableServiceImpl.createTable(table);
	}
	
	@PutMapping("establishment/table/{id}")
	public Table updateTable(@PathVariable("id") Table tableFromDB,  @RequestBody Table table) throws Exception {
		return tableServiceImpl.updateTable(tableFromDB, table);
	}
	
	@DeleteMapping("establishment/table/{id}")
	public Table deteleTable(@PathVariable("id") Table table) throws Exception {
		tableServiceImpl.deleteTable(table);
		return table;
	}
	
}
