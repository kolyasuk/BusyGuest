package iful.edu.bg.controller;

import java.util.List;
import java.util.function.BiConsumer;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iful.edu.bg.dto.EventType;
import iful.edu.bg.dto.ObjectType;
import iful.edu.bg.entity.BookedTable;
import iful.edu.bg.entity.Reputation;
import iful.edu.bg.entity.User;
import iful.edu.bg.entity.Views;
import iful.edu.bg.enums.Reputations;
import iful.edu.bg.repository.ReputationRepository;
import iful.edu.bg.repository.UserRepository;
import iful.edu.bg.service.BookedTableServiceImpl;
import iful.edu.bg.service.EstablishmentServiceImpl;
import iful.edu.bg.service.TableServiceImpl;
import iful.edu.bg.util.WsSender;

@RestController
@RequestMapping("/visitor")
public class VisitorController {
	private final static double RELIABLE_ORDERS_PROCENT=80;
	private final static int RELIABLE_ORDERS_NUMBER=80;
	
	private final  EstablishmentServiceImpl establishmentServiceImpl;
	private final  TableServiceImpl tableServiceImpl;
	
	private final BookedTableServiceImpl bookedTableServiceImpl;

	private final  UserRepository userRepository;

	private final  ReputationRepository reputationRepository;
	
	private final BiConsumer<EventType, BookedTable> wsSender;
	
	public VisitorController(EstablishmentServiceImpl establishmentServiceImpl, TableServiceImpl tableServiceImpl, BookedTableServiceImpl bookedTableServiceImpl, UserRepository userRepository, ReputationRepository reputationRepository,
			WsSender wsSender) {
		super();
		this.establishmentServiceImpl = establishmentServiceImpl;
		this.tableServiceImpl = tableServiceImpl;
		this.bookedTableServiceImpl = bookedTableServiceImpl;
		this.userRepository = userRepository;
		this.reputationRepository = reputationRepository;
		this.wsSender = wsSender.getSender(ObjectType.BOOKEDTABLE, Views.Id.class);
	}
	
	
	@GetMapping("bookedTable/{id}")
	public BookedTable getBookedTable(@PathVariable("id") String id) throws Exception {
		return bookedTableServiceImpl.getBookedTableById(id);
	}
	
	@GetMapping("{tableId}/bookedTable")
	public List<BookedTable> getEstablishmentBookedTable(@PathVariable("tableId") String id) throws Exception {
		return bookedTableServiceImpl.getActualBookedTableByTableId(id);
	}
	
	@GetMapping("{userId}/userBookedTable")
	public List<BookedTable> getUserBookedTables(@PathVariable("userId") String id) throws Exception {
		return bookedTableServiceImpl.getBookedTableByUserId(id);
	}
	
	@PostMapping("bookedTable")
	public BookedTable createBookedTable(@Valid @RequestBody BookedTable bookedTable) throws Exception {
		Reputation rep = bookedTable.getUser().getReputation();
		if(rep.getOrders()!=0) {
			double procent = (rep.getSuccessfulOrders()/rep.getOrders())*100;
			if(procent>=RELIABLE_ORDERS_PROCENT && rep.getOrders()>=RELIABLE_ORDERS_NUMBER)
				rep.setName(Reputations.RELIABLE);
			else
				rep.setName(Reputations.UNRELIABLE);
		}
		
		rep.setOrders(rep.getOrders()+1);
		reputationRepository.save(rep);
		
		BookedTable createdBookedTable = bookedTableServiceImpl.createBookedTable(bookedTable);
		wsSender.accept(EventType.CREATE, createdBookedTable);
		
		return createdBookedTable;
	}
	
	@DeleteMapping("bookedTable/{id}")
	public void deleteBooledTables(@PathVariable("id") BookedTable bookedTable) throws Exception {
		bookedTableServiceImpl.deleteBookedTable(bookedTable.get_id());
		wsSender.accept(EventType.REMOVE, bookedTable);
	}
	
	@PutMapping("/bookedTable/{id}")
	public BookedTable updateBookedTable(@PathVariable("id") BookedTable bookedTableFromDB, @RequestBody BookedTable bookedTable) throws Exception {
		BeanUtils.copyProperties(bookedTable, bookedTableFromDB, "id");
		BookedTable updatedBookedTable = bookedTableServiceImpl.createBookedTable(bookedTableFromDB);
        wsSender.accept(EventType.UPDATE, updatedBookedTable);
        
        return updatedBookedTable;
	}
	
	
	
	@PutMapping("profile")
	public User updateProfile(@Valid @RequestBody User user) throws Exception {
		return userRepository.save(user);
	}

	
	@GetMapping("checkUserBookedTable/{userId}/{estbId}")
	public boolean checkActualUserBookedTable(@PathVariable("userId") String userId, @PathVariable("estbId") String estbId) throws Exception {
		return bookedTableServiceImpl.checkActualBookedTableByEstablishmentId(userId, estbId);
	}
	
	@GetMapping("checkBookedTable/{table}")
	public List<BookedTable> checkActualBookedTable(@PathVariable("table") String estbId) throws Exception {
		return bookedTableServiceImpl.getActualBookedTableByTableId(estbId);
	}	
	

}
