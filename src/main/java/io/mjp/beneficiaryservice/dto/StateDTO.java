package io.mjp.beneficiaryservice.dto;

import java.io.Serializable;

public class StateDTO implements Serializable {

	private static final long serialVersionUID = 8249119141701214708L;

	private Long id;

	private String name;

	private String abbreviation;
	
	private CountryDTO country;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public CountryDTO getCountry() {
		return country;
	}

	public void setCountry(CountryDTO country) {
		this.country = country;
	}
}