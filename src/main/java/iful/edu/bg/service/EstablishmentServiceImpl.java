package iful.edu.bg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iful.edu.bg.dao.EstablishmentDAOImpl;
import iful.edu.bg.model.Establishment;

@Service
public class EstablishmentServiceImpl implements EstablishmentService {
	@Autowired
	private EstablishmentDAOImpl establishmentDAOImpl;

	@Override
	public List<Establishment> getEstablishmentList() {
		return establishmentDAOImpl.getEstablishmentList();
	}

	@Override
	public Establishment findByEmail(String email) throws Exception {
		return establishmentDAOImpl.findByEmail(email);
	}

	@Override
	public Establishment createEstablishment(Establishment establishment/* , String accountEmail */) {

		return establishmentDAOImpl.createEstablishment(establishment/* , accountEmail */);
	}

	@Override
	public void updateEstablishment(Establishment establishment, String accountEmail) {
		establishmentDAOImpl.updateEstablishment(establishment, accountEmail);
	}

}
