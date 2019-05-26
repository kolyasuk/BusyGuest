package iful.edu.bg.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Document
@Data
public class User implements Serializable {

	@Id
	@JsonView(Views.Id.class)
	private String id;
	private String name;
	private String userpic;
	private String email;
	
	@DBRef
	private Reputation reputation;
	private String phone;
	
	@DBRef
	private Role role;

}
