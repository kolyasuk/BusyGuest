package iful.edu.bg.entity;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookedTable {
	
	@Id
	@JsonView(Views.Id.class)
	private String _id;
	
	@DBRef
	@JsonView(Views.Id.class)
	@NotNull
	private Table table;
	
	@DBRef
	@JsonView(Views.Id.class)
	@NotNull
	private Establishment estb;
	
	@DBRef
	@JsonView(Views.Id.class)
	@NotNull
	private User user;
	
	@JsonView(Views.Id.class)
	@NotNull
	private LocalDateTime bookedOn;
	
	@JsonView(Views.FullData.class)
	private String comment;
	
	@JsonView(Views.Id.class)
	@NotNull
	private boolean accepted;
	
	@JsonView(Views.FullData.class)
	@NotNull
	private boolean done;
}
