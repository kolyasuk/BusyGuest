package iful.edu.bg.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Table {

	@Id
	private ObjectId _id;
	@DBRef
	private Establishment estb;
	private int tableNum;
	private int seats;
	@DBRef
	private TableStatus status;

}
