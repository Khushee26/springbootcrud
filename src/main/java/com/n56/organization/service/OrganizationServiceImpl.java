package com.n56.organization.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n56.organization.baseresponse.BaseResponse;
import com.n56.organization.baseresponse.StatusEnum;
import com.n56.organization.exceptionhandler.OrganizationDuplicationDataException;
import com.n56.organization.exceptionhandler.OrganizationNotFoundException;
import com.n56.organization.model.Country;
import com.n56.organization.model.Organization;
import com.n56.organization.repository.OrganizationRepository;
import com.n56.organization.request.OrganizationRequest;
import com.n56.organization.response.OrganizationCustomResponse;
import com.n56.organization.response.OrganizationResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	CountryService countryService;

	@Override
	public void saveOrganizationData(OrganizationRequest organizationRequest, Integer organizationId) {
		LOGGER.info("Entry: saveOrganizationData :: OrganizationServiceImpl");
		if(Objects.nonNull(organizationRequest)) {
			if (!organizationCode(organizationRequest.getOrganizationCode(), organizationId)) {
				throw new OrganizationDuplicationDataException(StatusEnum.NOT_FOUND, "Organization Code Already Exists");
			}
			Organization organization = organizationRepository.findByOrganizationCode(organizationRequest.getOrganizationCode());
			if (organization == null) {
				organization = new Organization();
				organization.setCreatedBy(1);
				organization.setOrganizationName(organizationRequest.getOrganizationName());
				organization.setOrganizationCode(organizationRequest.getOrganizationCode());
				organization.setAddressLine1(organizationRequest.getOrganizationAddressLine1());
				organization.setAddressLine2(organizationRequest.getOrganizationAddressLine2());
				organization.setAddressLine3(organizationRequest.getOrganizationAddressLine3());
				organization.setCity(organizationRequest.getOrganizationCity());
				organization.setCountry(countryService.findCountryById(organizationRequest.getOrganizationCountryId())	);
				organizationRepository.save(organization);
			} else {
				throw new OrganizationDuplicationDataException(StatusEnum.NOT_FOUND, "Organization Data Already Existss");
			}
		}
	}

	@Override
	public BaseResponse updateOrganizaitonData(OrganizationRequest organizationRequest, Integer organizationId) {
		BaseResponse baseResponse = new BaseResponse();
		if (Objects.nonNull(organizationRequest) && Objects.nonNull(organizationId)) {
			Organization organization = null;
			Optional<Organization> optionalOrganization = null;
			if (!checkDuplicateData(organizationRequest.getOrganizationName(), organizationId)) {
				throw new OrganizationDuplicationDataException(StatusEnum.NOT_FOUND, " Exists");
			} else if (!organizationCode(organizationRequest.getOrganizationCode(), organizationId)) {
				throw new OrganizationDuplicationDataException(StatusEnum.NOT_FOUND, "Organization Code Already Exists");
			}
			optionalOrganization = organizationRepository.findById(organizationId);
			if (!optionalOrganization.isEmpty()) {
				organization = optionalOrganization.get();
				organization.setOrganizationName(organizationRequest.getOrganizationName());
				organization.setOrganizationCode(organizationRequest.getOrganizationCode());
				organization.setAddressLine1(organizationRequest.getOrganizationAddressLine1());
				organization.setAddressLine2(organizationRequest.getOrganizationAddressLine2());
				organization.setAddressLine3(organizationRequest.getOrganizationAddressLine3());
				organization.setCity(organizationRequest.getOrganizationCity());
				Country country = countryService.findCountryById(organizationRequest.getOrganizationCountryId());
				organization.setCountry(country);
				organizationRepository.save(organization);
				baseResponse.setStatus(StatusEnum.SUCCESS);
				baseResponse.setMessage("Data Updated Successfully");
			} else {
				throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Organization ID Not Found");
			}
		} else {
			baseResponse.setStatus(StatusEnum.SUCCESS);
			baseResponse.setMessage("organizationrequst and id null in update organization data");
		}
		return baseResponse;
	}

	@Override
	public BaseResponse deleteOrganizationData(Integer organizationId) {
		LOGGER.debug("Entry: deleteOrganizationData :: OrganizationServiceImpl");
		if(Objects.nonNull(organizationId)) {
			Optional<Organization> organization = organizationRepository.findById(organizationId);
			if (!organization.isEmpty()) {
				organizationRepository.delete(organization.get());
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setMessage("Organization Deleted Succsessfully ");
				baseResponse.setStatus(StatusEnum.SUCCESS);
				return baseResponse;
			} else {
				throw new OrganizationDuplicationDataException(StatusEnum.NOT_FOUND, "Organization ID does Not Exist");
			}
		} else {
			throw new OrganizationDuplicationDataException(StatusEnum.NOT_FOUND, "Organization ID does Not Exist");
		}
	}	
		
	@Override
	public OrganizationResponse getOrganizationDataById(Integer organizationId) {
		LOGGER.debug("Entry: getOrganizationDataById :: OrganizationServiceImpl");
		if(Objects.nonNull(organizationId)) {
			OrganizationResponse organizationResponse = new OrganizationResponse();
			List<OrganizationCustomResponse> organizationResponsesList = new ArrayList<>();
			Optional<Organization> orgResponse = organizationRepository.findById(organizationId);
			if (orgResponse.isPresent()) {
				Organization organization = orgResponse.get();
				OrganizationCustomResponse organizationCustomResponse = getOrganizationDataInCustom(organization);
				organizationResponsesList.add(organizationCustomResponse);
				organizationResponse.setOrganizationResponsesList(organizationResponsesList);
				organizationResponse.setMessage("Organization Fetched Successfully");
				organizationResponse.setStatus(StatusEnum.SUCCESS);
				organizationResponse.setOrganizationResponsesList(organizationResponsesList);
				return organizationResponse;
			} else {
				throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "This Does Not Exist");
			}
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Does Not Exist");
		}
	}

	@Override
	public OrganizationResponse getOrganizationInList() {
		LOGGER.debug("Entry: getOrganizationInList :: OrganizationServiceImpl");
		OrganizationResponse organizationResponse = new OrganizationResponse();
		OrganizationCustomResponse organizationCustomResponse = null;
		List<Organization> organizationList = organizationRepository.findAll();
		List<OrganizationCustomResponse> organizationResponseList = new ArrayList<>();
		for (Organization organization : organizationList) {
			organizationCustomResponse = getOrganizationDataInCustom(organization);
			organizationResponseList.add(organizationCustomResponse);
		}
		organizationResponse.setMessage("Organization List Fetched Successfully");
		organizationResponse.setStatus(StatusEnum.SUCCESS);
		organizationResponse.setOrganizationResponsesList(organizationResponseList);
		return organizationResponse;
	}

	private OrganizationCustomResponse getOrganizationDataInCustom(Organization organization) {
		LOGGER.debug("Entry: findOrganizationById :: OrganizationServiceImpl");
		if (Objects.nonNull(organization)) {
			OrganizationCustomResponse organizaionCustomResponse = new OrganizationCustomResponse();
			organizaionCustomResponse.setOrganizationName(organization.getOrganizationName());
			organizaionCustomResponse.setOrganizationCode(organization.getOrganizationCode());
			organizaionCustomResponse.setAddressLine1(organization.getAddressLine1());
			organizaionCustomResponse.setAddressLine2(organization.getAddressLine2());
			organizaionCustomResponse.setAddressLine3(organization.getAddressLine3());
			organizaionCustomResponse.setCity(organization.getCity());
			organizaionCustomResponse.setCountryId(organization.getCountry().getCountryId());
			organizaionCustomResponse.setCountryName(organization.getCountry().getCountryName());
			return organizaionCustomResponse;
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Does Not Exist");
		}
	}

	@Override
	public Organization findOrganizationById(Integer organizationId) {
		LOGGER.info("Entry: findOrganizationById :: OrganizationServiceImpl");
		Organization organization = null;
		if (Objects.nonNull(organizationId)) {
			Optional<Organization> organizationOptionalData = organizationRepository.findById(organizationId);
				organization = organizationOptionalData.get();
			LOGGER.info("Exit: findOrganizationById :: OrganizationServiceImpl");
		} else {
			LOGGER.info("OrganizationId is null in find organization by ID");
		}
		return organization;
	}

	public boolean checkDuplicateData(String organizationName, Integer organizationId) {
		LOGGER.info("Entry: checkDuplicateData :: OrganizationServiceImpl");
		if(Objects.nonNull(organizationName)) {
			boolean valid = false;
			Organization organization = null;
			if (Objects.nonNull(organizationId)) {
				organization = organizationRepository.findByOrganizationNameAndOrganizationIdNot(organizationName,
						organizationId);
			} else {
				organization = organizationRepository.findByOrganizationName(organizationName);
			}
			if (Objects.isNull(organization)) {
				valid = true;
			}
			LOGGER.info("Entry: checkDuplicateData :: OrganizationServiceImpl");
			return valid;
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Does Not Exist");
		}	
	}

	public boolean organizationCode(String organizationCode, Integer organizationId) {
		LOGGER.info("Entry: checkDuplicateData :: OrganizationServiceImpl");
		if(Objects.nonNull(organizationCode)) {
			boolean valid = false;
			Organization organization = null;
			if (Objects.nonNull(organizationId)) {
				organization = organizationRepository.findByOrganizationCodeAndOrganizationIdNot(organizationCode,
						organizationId);
			} else {
				organization = organizationRepository.findByOrganizationCode(organizationCode);
			}
			if (Objects.isNull(organization)) {
				valid = true;
			}
			LOGGER.info("Entry: checkDuplicateData :: OrganizationServiceImpl");
			return valid;
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Does Not Exist");
		}
	}
}