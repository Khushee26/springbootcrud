package com.n56.organization.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n56.organization.baseresponse.BaseResponse;
import com.n56.organization.baseresponse.StatusEnum;
import com.n56.organization.exceptionhandler.OrganizationDuplicationDataException;
import com.n56.organization.exceptionhandler.OrganizationNotFoundException;
import com.n56.organization.model.Country;
import com.n56.organization.repository.CountryRepository;
import com.n56.organization.request.CountryRequest;
import com.n56.organization.response.CountryCustomResponse;
import com.n56.organization.response.CountryResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryRepository countryRepository;

	@Override
	public BaseResponse saveCountryData(@Valid CountryRequest countryRequest, Integer countryId) {
		BaseResponse baseResponse = null;
		if(Objects.nonNull(countryRequest)) {
			LOGGER.info("Entry: saveCountryData :: CountryServiceImpl");
			Country country = null;
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
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, " Country Does not Exists");
		}
		return baseResponse;
	}

	@Override
	public BaseResponse updateCountryData(@Valid CountryRequest countryRequest, Integer countryId) {
	LOGGER.info("Entry: updateCountryData :: CountryServiceImpl");
	BaseResponse baseResponse = null;
		if(Objects.nonNull(countryRequest) && Objects.nonNull(countryId)) {
			Country country = null;
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
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Countryrequst and id null in update organization data");
		}	
		return baseResponse;
	}

	@Override
	public BaseResponse deleteCountryData(@Valid Integer countryId) {
		LOGGER.info("Entry: findCountryById :: CountryServiceImpl");
		if (Objects.nonNull(countryId)) {
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
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Country data not Exists");
		}
	}

	@Override
	public Country findCountryById(@Valid Integer countryId) {
		LOGGER.info("Entry: findCountryById :: CountryServiceImpl");
		if(Objects.nonNull(countryId)) {
			Optional<Country> countryOptionalData = countryRepository.findById(countryId);
			Country country = null;
			if (countryOptionalData.isPresent()) {
				country = countryOptionalData.get();
			} else {
				throw new OrganizationNotFoundException(StatusEnum.ID_NOT_EXISTS, "Country Id Does Not Exist");
			}
			LOGGER.info("Exit: findCountryById :: CountryServiceImpl");
			return country;
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Country data not Exists");
		}
	}

	@Override
	public CountryResponse getCountryDataById(@Valid Integer countryId) {
		LOGGER.info("Entry: getCountryDataById :: CountryServiceImpl");
		if(Objects.nonNull(countryId)) {
		Country country = null;
		CountryResponse countryResponse = new CountryResponse();
		List<CountryCustomResponse> countryResponseList = new ArrayList<>();
		country = findCountryById(countryId);
		countryResponseList.add(getCountryDataInCustom(country));
		countryResponse.setCountryCustomResponseList(countryResponseList);
		countryResponse.setMessage("Country Data Fetched Successfully.");
		countryResponse.setStatus(StatusEnum.SUCCESS);
		return countryResponse;
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Country data not Exists");
		}
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

	public CountryCustomResponse getCountryDataInCustom(@Valid Country country) {
		LOGGER.info("Entry: getCountryDataById :: CountryServiceImpl");
		if(Objects.nonNull(country)) {
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Country Data Not Exists");
		}
		CountryCustomResponse countryCustomResponse = new CountryCustomResponse();
		countryCustomResponse.setCountryName(country.getCountryName());
		countryCustomResponse.setCountryCode(country.getCountryCode());
		return countryCustomResponse;
	}

	public boolean checkDuplicateData(@Valid String countryName, Integer countryId) {
		LOGGER.info("Entry: checkDuplicateData :: CountryServiceImpl");
		if(Objects.nonNull(countryName)) {
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
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Country data not Exists");
		}
	}

	public boolean countryCode(@Valid String countryCode, Integer countryId) {
		LOGGER.info("Entry: countryCode :: CountryServiceImpl");
		if(Objects.nonNull(countryCode)) {
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
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Country data not Exists");
		}
	}
}