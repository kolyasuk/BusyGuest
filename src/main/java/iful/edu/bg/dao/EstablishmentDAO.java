package iful.edu.bg.dao;

import java.util.List;

import iful.edu.bg.model.Establishment;

public interface EstablishmentDAO {

	List<Establishment> getEstablishmentList();

	Establishment findByEmail(String email) throws Exception;

	Establishment createEstablishment(Establishment establishment/* , String accountEmail */);

	void updateEstablishment(Establishment establishment, String accountEmail);

}
