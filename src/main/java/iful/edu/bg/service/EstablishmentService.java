
package iful.edu.bg.service;

import java.util.List;

import iful.edu.bg.model.Establishment;

public interface EstablishmentService {

	List<Establishment> getEstablishmentList();

	Establishment findByEmail(String email) throws Exception;

	Establishment createEstablishment(Establishment establishment/* , String accountEmail */);

	void updateEstablishment(Establishment establishment, String accountEmail);

}
