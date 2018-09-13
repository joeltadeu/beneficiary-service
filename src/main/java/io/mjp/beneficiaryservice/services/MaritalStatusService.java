package io.mjp.beneficiaryservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import io.mjp.beneficiaryservice.domains.MaritalStatus;
import io.mjp.beneficiaryservice.dto.MaritalStatusDTO;
import io.mjp.beneficiaryservice.repositories.MaritalStatusRepository;
import io.mjp.beneficiaryservice.services.exceptions.DataIntegrityException;
import io.mjp.beneficiaryservice.services.exceptions.ObjectNotFoundException;

@Service
public class MaritalStatusService {

	@Autowired
	private MaritalStatusRepository repository;

	public MaritalStatus find(Long id) {
		Optional<MaritalStatus> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Marital status not found! Id: " + id));
	}

	public MaritalStatus insert(MaritalStatus maritalStatus) {
		return repository.save(maritalStatus);
	}

	public MaritalStatus update(MaritalStatus maritalStatus) {
		MaritalStatus newMaritalStatus = find(maritalStatus.getId());
		updateData(newMaritalStatus, maritalStatus);
		return repository.save(newMaritalStatus);
	}

	private void updateData(MaritalStatus newObj, MaritalStatus obj) {
		newObj.setDescription(obj.getDescription());
	}

	public void delete(Long id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("You can not delete a marital status associated for beneficiary");
		}
	}

	public List<MaritalStatus> findAll() {
		return repository.findAll();
	}

	public Page<MaritalStatus> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public MaritalStatus fromDTO(MaritalStatusDTO dto) {
		return new MaritalStatus(dto.getId(), dto.getDescription());
	}
}
