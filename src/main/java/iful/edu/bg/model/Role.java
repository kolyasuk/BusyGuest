package iful.edu.bg.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import iful.edu.bg.enums.Roles;
import lombok.Data;

@Document
@Data
public class Role implements Serializable{
	@Id
	private String _id;

	@Indexed(unique = true, direction = IndexDirection.DESCENDING)
	private Roles name;

}
