package com.n56.organization.service;

import org.springframework.stereotype.Service;

import com.n56.organization.baseresponse.BaseResponse;
import com.n56.organization.model.Organization;
import com.n56.organization.request.OrganizationRequest;
import com.n56.organization.response.OrganizationResponse;

@Service
public interface OrganizationService {

	void saveOrganizationData(OrganizationRequest organizationRequest, Integer organizationId);

	public BaseResponse deleteOrganizationData(Integer organizationId);

	public OrganizationResponse getOrganizationDataById(Integer organizationId);

	public OrganizationResponse getOrganizationInList();

	public BaseResponse updateOrganizaitonData(OrganizationRequest organizationRequest, Integer organizationId);

	public Organization findOrganizationById(Integer organizationId);

}
