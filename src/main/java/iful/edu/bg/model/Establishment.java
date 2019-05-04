package iful.edu.bg.model;

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
	private String email;
	
	@JsonView(Views.Id.class)
	private String name;
	
	private String address;
	
	private String phone;
	
	private String workSchedule;
	
	private String description;
	
	private boolean show;

}
