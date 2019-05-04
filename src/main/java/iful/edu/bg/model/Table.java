package iful.edu.bg.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	@NotEmpty
	private Establishment estb;
	@NotNull
	private int tableNum;
	private int seats;
	private TableStatuses status;

}
