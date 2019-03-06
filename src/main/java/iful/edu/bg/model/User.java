package iful.edu.bg.model;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class User implements Serializable {

	@Id
	private String id;
	private String name;
	private String userpic;
	private String email;
	private String gender;
	private String locale;
	
	@DBRef
	private Set<Role> roles;

}
