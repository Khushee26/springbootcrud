package com.n56.Organization.service;

import org.springframework.stereotype.Service;

import com.n56.Organization.BaseResponse.BaseResponse;
import com.n56.Organization.model.Country;
import com.n56.Organization.request.CountryRequest;
import com.n56.Organization.response.CountryResponse;

@Service
public interface CountryService {

	// save
	public BaseResponse saveCountryData(CountryRequest countryRequest, Integer countryId);
	
	// update
	public BaseResponse updateCountryData(CountryRequest countryRequest, Integer countryId);

	// single country data
	public CountryResponse getCountryDataById(Integer countryId);

	// delete
	public BaseResponse deleteCountryData(Integer countryId);

	// country list
	public CountryResponse getCountryInList();
	
	public Country findCountryById(Integer countryId);

}
