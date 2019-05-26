package iful.edu.bg.entity;

import java.util.HashMap;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document
@Data
@AllArgsConstructor
public class EstablishmentCoordinates {
	public EstablishmentCoordinates() {
		// TODO Auto-generated constructor stub
	}
	@Id
	private String estbId;
	@NotNull
	private int width;
	@NotNull
	private int height;
	private HashMap<String, TableCoordinates> tables;
	private HashMap<String, SignsCoordinates> signs;
	
	@Data
	@AllArgsConstructor
	public static class TableCoordinates {
		private int x;
		private int y;
		private int width;
		private int height;
	}
	
	@Data
	@AllArgsConstructor
	public static class SignsCoordinates {
		@Id
		private String id;
		private int x;
		private int y;
	}
	
	
}
