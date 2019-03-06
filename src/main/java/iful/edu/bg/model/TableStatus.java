package iful.edu.bg.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class TableStatus {

	public TableStatus() {
	}

	public TableStatus(String status) {
		this.status = status;
	}

	@Id
	private ObjectId _id;

	@Indexed(unique = true, direction = IndexDirection.DESCENDING)
	private String status;

}
