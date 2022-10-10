package com.n56.Organization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n56.Organization.BaseResponse.BaseResponse;
import com.n56.Organization.request.CountryRequest;
import com.n56.Organization.response.CountryResponse;
import com.n56.Organization.service.CountryService;

@RestController
@RequestMapping("/country")
public class CountryController {

	@Autowired
	private CountryService countryService; 

	@PostMapping("/save")
	private ResponseEntity<?> saveCountryData(@RequestBody CountryRequest countryRequest) {
		BaseResponse baseResponse = countryService.saveCountryData(countryRequest, null); 
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);	
	}

	@PostMapping("/update/{countryId}")
	private ResponseEntity<?> updateCountryData(@RequestBody CountryRequest countryRequest, @PathVariable("countryId") int countryId) {
		BaseResponse baseResponse = countryService.updateCountryData(countryRequest, countryId);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@DeleteMapping("/delete/{countryId}")
	private ResponseEntity<?> deleteCountryData(@PathVariable("countryId") int countryId) {
		BaseResponse baseResponse = countryService.deleteCountryData(countryId);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/get/{countryId}")
	private ResponseEntity<?> getCountryDataById(@PathVariable("countryId") int countryId) {
		CountryResponse countryResponse = new CountryResponse();
		countryResponse = countryService.getCountryDataById(countryId);
		return ResponseEntity.status(HttpStatus.OK).body(countryResponse);
	}

	@GetMapping("/getList")
	private ResponseEntity<?> getCountryInList() {
		CountryResponse countryResponse = countryService.getCountryInList();
		return ResponseEntity.status(HttpStatus.OK).body(countryResponse);
	}

}
