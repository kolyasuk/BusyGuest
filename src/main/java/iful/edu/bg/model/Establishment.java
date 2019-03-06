package iful.edu.bg.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Establishment {
	@Id
	private ObjectId _id;

	@Indexed(unique = true, direction = IndexDirection.DESCENDING)
	private String email;
	private String name;
	private String address;
	private String phone;
	private String workSchedule;
	private String description;

}
