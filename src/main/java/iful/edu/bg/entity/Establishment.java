package iful.edu.bg.entity;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document
@Data
@AllArgsConstructor
public class Establishment {
	@Id
	@JsonView(Views.Id.class)
	private String _id;

	@Indexed(unique = true, direction = IndexDirection.DESCENDING)
	@NotNull
	private String email;
	
	@JsonView(Views.Id.class)
	@Size(min = 1, max = 25, message 
    = "Name must be between {min} and {max} characters")
	private String name;
	
	@Size(min = 10, max = 35, message 
		    = "Address must be between {min} and {max} characters")
	private String address;
	
	@NotNull
	private ArrayList<String> phone;
	
	@Size(min = 4, max = 40, message 
		    = "Work schedule must be between {min} and {max} characters")
	private String workSchedule;
	
	@Size(min = 40, max = 400, message 
		    = "Description must be between {min} and {max} characters")
	private String description;
	
	@Size(min = 3, max = 35, message 
		    = "Cuisine must be between {min} and {max} characters")
	private String cuisine;
	
	@Size(min = 2, max = 10, message 
		    = "Average check must be between {min} and {max} characters")
	private String avgCheck;
	
	@NotNull
	private boolean show;
	
	@NotNull
	private boolean accepted;

}
