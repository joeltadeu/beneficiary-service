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

import io.mjp.beneficiaryservice.domains.Schooling;
import io.mjp.beneficiaryservice.dto.SchoolingDTO;
import io.mjp.beneficiaryservice.services.SchoolingService;

@RestController
@RequestMapping(value = "/schooling")
public class SchoolingResource {

	@Autowired
	private SchoolingService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Schooling> find(@PathVariable Long id) {
		Schooling obj = service.find(id);
		return ResponseEntity.ok(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody @Valid SchoolingDTO dto) {
		Schooling schooling = service.fromDTO(dto);
		schooling = service.insert(schooling);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(schooling.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody @Valid SchoolingDTO dto, @PathVariable Long id) {
		Schooling schooling = service.fromDTO(dto);
		schooling.setId(id);
		service.update(schooling);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<SchoolingDTO>> findAll() {
		List<Schooling> list = service.findAll();
		List<SchoolingDTO> listDTO = list.stream().map(obj -> new SchoolingDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok(listDTO);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<SchoolingDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "description") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Schooling> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<SchoolingDTO> listDTO = list.map(obj -> new SchoolingDTO(obj));
		return ResponseEntity.ok(listDTO);
	}
}