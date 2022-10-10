package com.n56.Organization.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.n56.Organization.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
	
	Country findByCountryName(String countryName);
	
	Country findByCountryNameAndCountryIdNot(String countryName, Integer countryId);
	
	Country findByCountryCode(String code);
	
	Country findByCountryCodeAndCountryIdNot (String countryCode, Integer countryId); 

}
