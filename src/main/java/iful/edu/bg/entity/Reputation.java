package iful.edu.bg.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

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
	@NotNull
	private Reputations name;
	private double orders;
	private double successfulOrders;
}
