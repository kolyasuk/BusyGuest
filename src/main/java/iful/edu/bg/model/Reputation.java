package iful.edu.bg.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import iful.edu.bg.enums.Reputations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reputation implements Serializable{
	@Id
	private String id;
	private Reputations name;
	private double orders;
	private double successfulOrders;
}
