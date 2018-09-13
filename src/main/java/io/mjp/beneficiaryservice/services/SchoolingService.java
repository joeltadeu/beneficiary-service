package io.mjp.beneficiaryservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import io.mjp.beneficiaryservice.domains.Schooling;
import io.mjp.beneficiaryservice.dto.SchoolingDTO;
import io.mjp.beneficiaryservice.repositories.SchoolingRepository;
import io.mjp.beneficiaryservice.services.exceptions.DataIntegrityException;
import io.mjp.beneficiaryservice.services.exceptions.ObjectNotFoundException;

@Service
public class SchoolingService {

	@Autowired
	private SchoolingRepository repository;

	public Schooling find(Long id) {
		Optional<Schooling> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Schooling not found! Id: " + id));
	}

	public Schooling insert(Schooling schooling) {
		return repository.save(schooling);
	}

	public Schooling update(Schooling schooling) {
		Schooling newSchooling = find(schooling.getId());
		updateData(newSchooling, schooling);
		return repository.save(newSchooling);
	}

	private void updateData(Schooling newObj, Schooling obj) {
		newObj.setDescription(obj.getDescription());
	}

	public void delete(Long id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("You can not delete a schooling associated for beneficiary");
		}
	}

	public List<Schooling> findAll() {
		return repository.findAll();
	}

	public Page<Schooling> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Schooling fromDTO(SchoolingDTO dto) {
		return new Schooling(dto.getId(), dto.getDescription());
	}
}
