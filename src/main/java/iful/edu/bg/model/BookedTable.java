package iful.edu.bg.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document
@Data
@AllArgsConstructor
public class BookedTable {

	@DBRef
	private Table table;
	@DBRef
	private User user;
	private Date bookedOn;
	private boolean done;
}
