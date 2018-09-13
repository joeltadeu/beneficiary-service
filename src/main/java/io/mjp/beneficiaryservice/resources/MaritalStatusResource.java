package io.mjp.beneficiaryservice.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.mjp.beneficiaryservice.domains.MaritalStatus;
import io.mjp.beneficiaryservice.dto.MaritalStatusDTO;
import io.mjp.beneficiaryservice.services.MaritalStatusService;

@RestController
@RequestMapping(value = "/maritalstatus")
public class MaritalStatusResource {

	@Autowired
	private MaritalStatusService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<MaritalStatus> find(@PathVariable Long id) {
		MaritalStatus obj = service.find(id);
		return ResponseEntity.ok(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody @Valid MaritalStatusDTO dto) {
		MaritalStatus maritalStatus = service.fromDTO(dto);
		maritalStatus = service.insert(maritalStatus);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(maritalStatus.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody @Valid MaritalStatusDTO dto, @PathVariable Long id) {
		MaritalStatus maritalStatus = service.fromDTO(dto);
		maritalStatus.setId(id);
		service.update(maritalStatus);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<MaritalStatusDTO>> findAll() {
		List<MaritalStatus> list = service.findAll();
		List<MaritalStatusDTO> listDTO = list.stream().map(obj -> new MaritalStatusDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok(listDTO);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<MaritalStatusDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "description") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<MaritalStatus> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<MaritalStatusDTO> listDTO = list.map(obj -> new MaritalStatusDTO(obj));
		return ResponseEntity.ok(listDTO);
	}
}