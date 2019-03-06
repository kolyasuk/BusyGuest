package iful.edu.bg.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Role implements Serializable{
	@Id
	private ObjectId _id;

	@Indexed(unique = true, direction = IndexDirection.DESCENDING)
	private String role;

}
