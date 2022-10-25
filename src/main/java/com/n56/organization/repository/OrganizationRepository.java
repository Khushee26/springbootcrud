package com.n56.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.n56.organization.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

	Organization findByOrganizationName(String organizationName);

	Organization findByOrganizationCode(String organizationCode);

	Organization findByOrganizationNameAndOrganizationIdNot(String organizationName, Integer organizationId);

	Organization findByOrganizationCodeAndOrganizationIdNot(String organizationCode, Integer organizationId);

}