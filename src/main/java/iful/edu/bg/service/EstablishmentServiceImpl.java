package iful.edu.bg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iful.edu.bg.model.Establishment;
import iful.edu.bg.repository.EstablishmentRepository;

@Service
public class EstablishmentServiceImpl implements EstablishmentService {
	
	@Autowired
	private EstablishmentRepository establishmentRepository;

	@Override
	public List<Establishment> getEstablishmentList() {
		return establishmentRepository.findAll();
	}

	@Override
	public Establishment findById(String id) throws Exception {
		Optional<Establishment> establishment = establishmentRepository.findById(id);
		if (establishment.isPresent())
			return establishment.get();
		else
			throw new Exception("Not found");
	}

	@Override
	public Establishment createEstablishment(Establishment establishment/* , String accountEmail */) {
		try {
			// Establishment establishmentFromDB = findByEmail(accountEmail);
			// establishment.set_id(establishmentFromDB.get_id());
			// establishment.setEmail(accountEmail);
			return establishmentRepository.save(establishment);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateEstablishment(Establishment establishment, String accountEmail) {
//		try {
//			Establishment establishmentFromDB = findByEmail(accountEmail);
//			establishment.set_id(establishmentFromDB.get_id());
//			establishment.setEmail(accountEmail);
//			establishmentRepository.save(establishment);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
