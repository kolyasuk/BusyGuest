package iful.edu.bg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iful.edu.bg.entity.Establishment;
import iful.edu.bg.repository.EstablishmentRepository;
import javassist.NotFoundException;

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
			throw new NotFoundException("Establishment Not Found");
	}

	@Override
	public Establishment createEstablishment(Establishment establishment) {
		establishment.setAccepted(false);
		return establishmentRepository.save(establishment);
	}

	@Override
	public Establishment updateEstablishment(Establishment establishmentFromDB, Establishment establishment) {
		BeanUtils.copyProperties(establishment, establishmentFromDB, "id"); 
		return establishmentRepository.save(establishmentFromDB);
	}

}
