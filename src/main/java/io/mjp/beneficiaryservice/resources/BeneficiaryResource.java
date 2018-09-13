package io.mjp.beneficiaryservice.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

import io.mjp.beneficiaryservice.domains.Beneficiary;
import io.mjp.beneficiaryservice.dto.BeneficiaryCreateDTO;
import io.mjp.beneficiaryservice.dto.BeneficiaryViewDTO;
import io.mjp.beneficiaryservice.services.BeneficiaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping(value = "/beneficiaries")
@Api(tags = { "Beneficiary Resource" })
@SwaggerDefinition(tags = { @Tag(name = "Beneficiary Resource", description = "Manage beneficiaries") })
public class BeneficiaryResource {

	@Autowired
	private BeneficiaryService service;

	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Search a beneficiary with an CPF", response = Beneficiary.class)
	@RequestMapping(value = "/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<Beneficiary> find(
			@ApiParam(value = "CPF to find the beneficiary", required = true) @PathVariable String cpf) {
		Beneficiary beneficiary = service.findByCpf(cpf);
		return ResponseEntity.ok(beneficiary);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Add a beneficiary")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody @Valid BeneficiaryCreateDTO dto) {
		Beneficiary beneficiary = service.fromDTO(dto);
		beneficiary.setCpf(dto.getCpf());
		beneficiary = service.insert(beneficiary);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cpf}").buildAndExpand(beneficiary.getCpf())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Update a beneficiary")
	@RequestMapping(value = "/{cpf}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody @Valid BeneficiaryCreateDTO dto,
			@ApiParam(value = "CPF to update the beneficiary", required = true) @PathVariable String cpf) {
		Beneficiary beneficiary = service.findByCpf(cpf);
		beneficiary = service.fromDTO(dto);
		service.update(beneficiary);
		return ResponseEntity.noContent().build();
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Delete a beneficiary")
	@RequestMapping(value = "/{cpf}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(
			@ApiParam(value = "CPF to delete the beneficiary", required = true) @PathVariable String cpf) {
		Beneficiary beneficiary = service.findByCpf(cpf);
		service.delete(beneficiary.getId());
		return ResponseEntity.noContent().build();
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "View a list of beneficiaries", response = Beneficiary.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<BeneficiaryViewDTO>> findAll() {
		List<Beneficiary> list = service.findAll();
		List<BeneficiaryViewDTO> listDTO = list.stream().map(obj -> new BeneficiaryViewDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok(listDTO);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<BeneficiaryViewDTO>> findPage(
			@ApiParam(value = "Number of the current page", required = true) @RequestParam(value = "page", defaultValue = "0") Integer page,
			@ApiParam(value = "Number of records per page", required = true) @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@ApiParam(value = "Column to order by the result", required = true) @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@ApiParam(value = "Ascending or descending order to view the result", required = true) @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Beneficiary> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<BeneficiaryViewDTO> listDTO = list.map(obj -> new BeneficiaryViewDTO(obj));
		return ResponseEntity.ok(listDTO);
	}
}
