package com.n56.Organization.service;

import org.springframework.stereotype.Service;

import com.n56.Organization.BaseResponse.BaseResponse;
import com.n56.Organization.model.Organization;
import com.n56.Organization.request.OrganizationRequest;
import com.n56.Organization.response.OrganizationResponse;

@Service
public interface OrganizationService {

	void saveOrganizationData(OrganizationRequest organizationRequest, Integer organizationId);

	public BaseResponse deleteOrganizationData(Integer organizationId);

	public OrganizationResponse getOrganizationDataById(Integer organizationId);

	public OrganizationResponse getOrganizationInList();

	public BaseResponse updateOrganizaitonData(OrganizationRequest organizationRequest, Integer organizationId);

	public Organization findOrganizationById(Integer organizationId);

}
