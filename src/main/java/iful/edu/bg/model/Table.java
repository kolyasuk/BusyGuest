package iful.edu.bg.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import iful.edu.bg.enums.TableStatuses;
import lombok.AllArgsConstructor;
import lombok.Data;

@Document
@Data
@AllArgsConstructor
public class Table {

	public Table() {
		// TODO Auto-generated constructor stub
	}
	@Id
	@JsonView(Views.Id.class)
	private String _id;
	@DBRef
	private Establishment estb;
	private int tableNum;
	private int seats;
	private TableStatuses status;

}
