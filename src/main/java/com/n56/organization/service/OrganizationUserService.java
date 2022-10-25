package com.n56.organization.service;

import org.springframework.stereotype.Service;

import com.n56.organization.baseresponse.BaseResponse;
import com.n56.organization.model.OrganizationUser;
import com.n56.organization.request.OrganizationUserRequest;
import com.n56.organization.response.OrganizationUserResponse;

@Service
public interface OrganizationUserService {
	
	BaseResponse saveOrganizationUserData(OrganizationUserRequest organizationUserRequest, Integer organizationUserId);
	
	OrganizationUser findOrganizationUserById(Integer organizationUserId);

	BaseResponse deleteOrganizationUserData(Integer organizationUserId);

	OrganizationUserResponse getOrganizationUserDataById(Integer organizationUserId);

	OrganizationUserResponse getOrganizationUserList();

}