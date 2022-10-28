package com.n56.organization.service;

import static java.util.Objects.nonNull;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.n56.organization.baseresponse.BaseResponse;
import com.n56.organization.baseresponse.StatusEnum;
import com.n56.organization.exceptionhandler.OrganizationDuplicationDataException;
import com.n56.organization.exceptionhandler.OrganizationNotFoundException;
import com.n56.organization.model.Country;
import com.n56.organization.model.MstUser;
import com.n56.organization.model.Organization;
import com.n56.organization.model.OrganizationUser;
import com.n56.organization.repository.MstUserRepository;
import com.n56.organization.repository.OrganizationUserRepository;
import com.n56.organization.request.OrganizationUserRequest;
import com.n56.organization.response.OrganizationUserCustomResponse;
import com.n56.organization.response.OrganizationUserResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrganizationUserServiceImpl implements OrganizationUserService {

	@Autowired
	OrganizationUserRepository organizationUserRepository;

	@Autowired
	MstUserRepository mstUserRepository;

	@Autowired
	OrganizationService organizationService;

	@Autowired
	CountryService countryService;

	@Transactional
	@Override
	public BaseResponse saveOrganizationUserData(OrganizationUserRequest organizationUserRequest,
			Integer organizationUserId) {
		LOGGER.info("Entry: saveOrganizationUserData :: OrganizationUserServiceImpl");
		Optional<OrganizationUser> optionalOrganizationUser = null;
		if (Objects.nonNull(organizationUserId)) {
			optionalOrganizationUser = organizationUserRepository.findById(organizationUserRequest.getOrganizationId());
		}
		if (Objects.nonNull(organizationUserId) && Objects.nonNull(organizationUserRequest)) {
			optionalOrganizationUser = organizationUserRepository.findById(organizationUserId);
		}
		OrganizationUser organizationUser = null;
		MstUser mstUser = null;
		BaseResponse baseResponse = new BaseResponse();
		if (optionalOrganizationUser == null || optionalOrganizationUser.isEmpty() || organizationUserId == null) {
			LOGGER.info("Entry: save	OrganizationUserData :: OrganizationUserServiceImpl");
			// save code
			System.out.println("save code executed");
			// exceptions here
			if (!checkDuplicateEmail(organizationUserRequest.getOrganizationUserEmail(), organizationUserId)) {
				throw new OrganizationDuplicationDataException(StatusEnum.NOT_FOUND,
						"OrganizationUser Email Already Exists");
			}
			organizationUser = new OrganizationUser();
			mstUser = new MstUser();
			baseResponse.setStatus(StatusEnum.SUCCESS);
			baseResponse.setMessage("Organizationuser data saved successfully ");

		} else {
			// update code
			LOGGER.info("Entry: update	OrganizationUserData :: OrganizationUserServiceImpl");
			organizationUser = optionalOrganizationUser.get(); // opt out
			mstUser = organizationUser.getMstUser();

			baseResponse.setStatus(StatusEnum.SUCCESS);
			baseResponse.setMessage("Organizationuser data updated successfully ");
			System.out.println("update code executed");
		}
		// common code
		mstUser.setActive(true);
		mstUser.setEmail(organizationUserRequest.getOrganizationUserEmail());
		mstUser.setPassword(organizationUserRequest.getOrganizationUserPassword());
		mstUser = mstUserRepository.save(mstUser);
		organizationUser.setMstUser(mstUser);
		Organization organization = organizationService
				.findOrganizationById(organizationUserRequest.getOrganizationId());
		organizationUser.setOrganization(organization);
		Timestamp timestamp = Timestamp.from(Instant.now());
		organizationUser.setCreatedOn(timestamp);
		organizationUser.setUpdatedOn(timestamp);
		organizationUser.setAddressLine1(organizationUserRequest.getAddressLine1());
		organizationUser.setAddressLine2(organizationUserRequest.getAddressLine2());
		organizationUser.setAddressLine3(organizationUserRequest.getAddressLine3());
		organizationUser.setCity(organizationUserRequest.getOrganizationUserCity());
		organizationUser.setFirstName(organizationUserRequest.getFirstName());
		organizationUser.setLastName(organizationUserRequest.getLastName());
		organizationUser.setCreatedBy(1);
		organizationUser.setActive(true);
		Country country = countryService.findCountryById(organizationUserRequest.getCountryId());
		organizationUser.setCountry(country);
		organizationUserRepository.save(organizationUser);
		return baseResponse;
		// common code ended
	}

	@Override
	public BaseResponse deleteOrganizationUserData(Integer organizationUserId) {
		LOGGER.info("Entry: deleteOrganizationUserData :: OrganizationUserServiceImpl");
		if (Objects.nonNull(organizationUserId)) {
			OrganizationUser organizationUser = findOrganizationUserById(organizationUserId);
			if (nonNull(organizationUser)) {
				organizationUserRepository.delete(organizationUser);
				mstUserRepository.delete(organizationUser.getMstUser());
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setMessage("OrganizationUser Deleted Succsessfully ");
				baseResponse.setStatus(StatusEnum.SUCCESS);
				LOGGER.debug("Exit: deleteOrganizationUserData :: OrganizationUserServiceImpl");
				return baseResponse;
			} else {
				throw new OrganizationDuplicationDataException(StatusEnum.NOT_FOUND,
						"OrganizationUser ID does Not Exist");
			}
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "delete organization data");
		}
	}

	@Override
	public OrganizationUserResponse getOrganizationUserDataById(Integer organizationUserId) {
		LOGGER.debug("Entry: getOrganizationuserDataById :: OrganizationUserServiceImpl");
		if (Objects.nonNull(organizationUserId)) {
			Optional<OrganizationUser> organizationUserOptional = organizationUserRepository
					.findById(organizationUserId);
			OrganizationUser organizationUser = null;
			OrganizationUserResponse organizationUserResponse = null;
			if (organizationUserOptional.isPresent()) {
				organizationUser = organizationUserOptional.get();
				organizationUserResponse = new OrganizationUserResponse();
				organizationUserResponse.setStatus(StatusEnum.SUCCESS);
				organizationUserResponse.setMessage("OrganizationUser Fetched Successfully");
				organizationUserResponse.setOrganizationUserResponsesList(
						Arrays.asList(setOrganizationUserCustomResponse(organizationUser)));
				LOGGER.debug("Exit: findOrganizationUserById :: OrganizationUserServiceImpl");
				return organizationUserResponse;
			} else {
				throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Id Does Not Exist");
			}
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "Does Not Exist");
		}
	}

	private OrganizationUserCustomResponse setOrganizationUserCustomResponse(OrganizationUser organizationUser) {
		OrganizationUserCustomResponse organizationUserCustomResponse = null;
		if (Objects.nonNull(organizationUser)) {
			organizationUserCustomResponse = new OrganizationUserCustomResponse();
			organizationUserCustomResponse
					.setOrganizationName(organizationUser.getOrganization().getOrganizationName());
			organizationUserCustomResponse
					.setOrganizationCode(organizationUser.getOrganization().getOrganizationCode());
			organizationUserCustomResponse.setEmail(organizationUser.getMstUser().getEmail());
			organizationUserCustomResponse.setPassword(organizationUser.getMstUser().getPassword());
			organizationUserCustomResponse.setFirstname(organizationUser.getFirstName());
			organizationUserCustomResponse.setLastname(organizationUser.getLastName());
			organizationUserCustomResponse.setAddressLine1(organizationUser.getAddressLine1());
			organizationUserCustomResponse.setAddressLine2(organizationUser.getAddressLine2());
			organizationUserCustomResponse.setAddressLine3(organizationUser.getAddressLine3());
			organizationUserCustomResponse.setCity(organizationUser.getCity());
			organizationUserCustomResponse.setCountryId(organizationUser.getCountry().getCountryId());
			organizationUserCustomResponse.setCountryName(organizationUser.getCountry().getCountryName());
		}
		return organizationUserCustomResponse;
	}

	@Override
	public OrganizationUser findOrganizationUserById(Integer organizationUserId) {
		LOGGER.info("Entry: findOrganizationUserById :: OrganizationUserServiceImpl");
		OrganizationUser organizationuser = null;
		if (Objects.nonNull(organizationUserId)) {
			Optional<OrganizationUser> organizationUserOptionalData = organizationUserRepository
					.findById(organizationUserId);
			organizationuser = organizationUserOptionalData.get();
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "OrganizationUser Data Does Not Exist");
		}
		LOGGER.info("Exit: findOrganizationUserById :: OrganizationServiceImpl");
		return organizationuser;
	}

	@Override
	public OrganizationUserResponse getOrganizationUserList() {
		LOGGER.debug("Entry: getOrganizationUserInList :: OrganizationUserServiceImpl");
		List<OrganizationUser> organizationUsers = organizationUserRepository.findAll();
		OrganizationUserResponse organizationuserResponse = new OrganizationUserResponse();
		List<OrganizationUserCustomResponse> listOrganizationResponses = new ArrayList<>();
		if (nonNull(organizationUsers)) {
			for (OrganizationUser user : organizationUsers) {
				OrganizationUserCustomResponse organizationuserCustomResponse = new OrganizationUserCustomResponse();
				organizationuserCustomResponse.setOrganizationName(user.getOrganization().getOrganizationName());
				organizationuserCustomResponse.setOrganizationCode(user.getOrganization().getOrganizationCode());
				organizationuserCustomResponse.setEmail(user.getMstUser().getEmail());
				organizationuserCustomResponse.setPassword(user.getMstUser().getPassword());
				organizationuserCustomResponse.setFirstname(user.getFirstName());
				organizationuserCustomResponse.setLastname(user.getLastName());
				organizationuserCustomResponse.setAddressLine1(user.getAddressLine1());
				organizationuserCustomResponse.setAddressLine2(user.getAddressLine2());
				organizationuserCustomResponse.setAddressLine3(user.getAddressLine3());
				organizationuserCustomResponse.setCity(user.getCity());
				organizationuserCustomResponse.setCountryId(user.getOrganizationUserId());
				organizationuserCustomResponse.setCountryName(user.getCountry().getCountryName());
				listOrganizationResponses.add(organizationuserCustomResponse);
			}
			organizationuserResponse.setOrganizationUserResponsesList(listOrganizationResponses);
			organizationuserResponse.setStatus(StatusEnum.SUCCESS);
			organizationuserResponse.setMessage("OrganizationUser List Fetched Successfully");
			return organizationuserResponse;
		} else {
			throw new OrganizationNotFoundException(StatusEnum.NOT_FOUND, "This Does Not Exist");
		}
	}

	public boolean checkDuplicateEmail(String email, Integer userId) {
		LOGGER.debug("Entry: checkDuplicateEmail :: OrganizationUserServiceImpl");
		boolean valid = false;
		MstUser user = null;
		if (Objects.nonNull(userId)) {
			user = mstUserRepository.findByEmailIgnoreCaseAndUserIdNot(email, userId);
		} else {
			user = mstUserRepository.findByEmailIgnoreCase(email);
		}
		if (Objects.isNull(user)) {
			valid = true;
		}
		LOGGER.debug("Entry: checkDuplicateEmail :: OrganizationUserServiceImpl");
		return valid;
	}
}