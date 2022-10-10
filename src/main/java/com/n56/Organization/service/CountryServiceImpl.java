package com.n56.Organization.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n56.Organization.BaseResponse.BaseResponse;
import com.n56.Organization.BaseResponse.StatusEnum;
import com.n56.Organization.Repository.CountryRepository;
import com.n56.Organization.exceptionhandler.OrganizationDuplicationDataException;
import com.n56.Organization.exceptionhandler.OrganizationNotFoundException;
import com.n56.Organization.model.Country;
import com.n56.Organization.request.CountryRequest;
import com.n56.Organization.response.CountryCustomResponse;
import com.n56.Organization.response.CountryResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryRepository countryRepository;

	@Override
	public BaseResponse saveCountryData(CountryRequest countryRequest, Integer countryId) {
		LOGGER.info("Entry: findCountryById :: CountryServiceImpl");
		Country country = null;
		BaseResponse baseResponse = null;
		if (countryId == null) {

			if (!checkDuplicateData(countryRequest.getCountryName(), countryId)) {
				throw new OrganizationDuplicationDataException(StatusEnum.NOT_FOUND, "Country Name Already Exists");
			}

			else if (!countryCode(countryRequest.getCountryCode(), countryId)) {
				throw new OrganizationDuplicationDataException(StatusEnum.NOT_FOUND, "Country Code Already Exists");
			}

			country = new Country();
			country.setCreatedBy(1);
			country.setCountryName(countryRequest.getCountryName());
			country.setCountryCode(countryRequest.getCountryCode());
			baseResponse = new BaseResponse();
			baseResponse.setMessage("Country data saved successfully");
			baseResponse.setStatus(StatusEnum.SUCCESS);
			countryRepository.save(country);
		}
		return baseResponse;
	}

	@Override
	public BaseResponse updateCountryData(CountryRequest countryRequest, Integer countryId) {
		Country country = null;
		BaseResponse baseResponse = null;
		if (!checkDuplicateData(countryRequest.getCountryName(), countryId)) {
			throw new OrganizationDuplicationDataException(StatusEnum.NOT_FOUND, "Country Name Already Exists");
		}

		else if (!countryCode(countryRequest.getCountryCode(), countryId)) {
			throw new OrganizationDuplicationDataException(StatusEnum.NOT_FOUND, "Country Code Already Exists");
		}

		country = findCountryById(countryId);
		if (Objects.nonNull(country)) {
			country.setModifiedBy(1);
			country.setCountryName(countryRequest.getCountryName());
			country.setCountryCode(countryRequest.getCountryCode());
			baseResponse = new BaseResponse();
			baseResponse.setMessage("Country updated successfully");
			baseResponse.setStatus(StatusEnum.SUCCESS);
			countryRepository.save(country);
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Country ID Not Found");
		}
		return baseResponse;
	}

	@Override
	public BaseResponse deleteCountryData(Integer countryId) {
		LOGGER.info("Entry: findCountryById :: CountryServiceImpl");
		BaseResponse baseResponse = null;
		Country country = findCountryById(countryId);
		if (Objects.nonNull(country)) {
			countryRepository.delete(country);
			baseResponse = new BaseResponse();
			baseResponse.setMessage("Country Deleted successfully");
			baseResponse.setStatus(StatusEnum.SUCCESS);
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Country ID Not Found");
		}
		return baseResponse;
	}

	@Override
	public Country findCountryById(Integer countryId) {
		LOGGER.info("Entry: findCountryById :: CountryServiceImpl");
		Optional<Country> countryOptionalData = countryRepository.findById(countryId);
		Country country = null;
		if (countryOptionalData.isPresent()) {
			country = countryOptionalData.get();
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Country Does Not Exist");

		}
		LOGGER.info("Exit: findCountryById :: CountryServiceImpl");
		return country;
	}

	@Override
	public CountryResponse getCountryDataById(Integer countryId) {
		LOGGER.info("Entry: getCountryDataById :: CountryServiceImpl");
		Country country = null;
		CountryResponse countryResponse = new CountryResponse();
		List<CountryCustomResponse> countryResponseList = new ArrayList<>();
		country = findCountryById(countryId);
//		CountryCustomResponse countryCustomResponse = getCountryDataInCustom(country);
		countryResponseList.add(getCountryDataInCustom(country));
		countryResponse.setCountryCustomResponseList(countryResponseList);
		countryResponse.setMessage("Country Data Fetched Successfully.");
		countryResponse.setStatus(StatusEnum.SUCCESS);
		return countryResponse;
	}

	@Override
	public CountryResponse getCountryInList() {
		LOGGER.info("Entry: getCountryDataById :: CountryServiceImpl");
		CountryResponse countryResponse = new CountryResponse();
		List<Country> countryList = countryRepository.findAll();
		List<CountryCustomResponse> countryResponseList = new ArrayList<>();
		for (Country country : countryList) {
			countryResponseList.add(getCountryDataInCustom(country));
		}

		countryResponse.setCountryCustomResponseList(countryResponseList);
		countryResponse.setStatus(StatusEnum.SUCCESS);
		countryResponse.setMessage("Country List Fetched Successfully.");

		return countryResponse;
	}

	public CountryCustomResponse getCountryDataInCustom(Country country) {
		LOGGER.info("Entry: getCountryDataById :: CountryServiceImpl");
		CountryCustomResponse countryCustomResponse = new CountryCustomResponse();
		countryCustomResponse.setCountryName(country.getCountryName());
		countryCustomResponse.setCountryCode(country.getCountryCode());
		return countryCustomResponse;
	}

	public boolean checkDuplicateData(String countryName, Integer countryId) {
		LOGGER.info("Entry: checkDuplicateData :: CountryServiceImpl");
		boolean valid = false;
		Country country = null;
		if (Objects.nonNull(countryId)) { // edit
			country = countryRepository.findByCountryNameAndCountryIdNot(countryName, countryId);
		} else { // save
			country = countryRepository.findByCountryName(countryName);
		}
		if (Objects.isNull(country)) {
			valid = true;
		}
		LOGGER.info("Entry: checkDuplicateData :: CountryServiceImpl");
		return valid;
	}

	public boolean countryCode(String countryCode, Integer countryId) {
		LOGGER.info("Entry: countryCode :: CountryServiceImpl");
		boolean valid = false;
		Country country = null;
		if (Objects.nonNull(countryId)) { // edit
			country = countryRepository.findByCountryCodeAndCountryIdNot(countryCode, countryId);
		} else { // save
			country = countryRepository.findByCountryCode(countryCode);
		}
		if (Objects.isNull(country)) {
			valid = true;
		}
		LOGGER.info("Entry: countryCode :: CountryServiceImpl");
		return valid;
	}

}