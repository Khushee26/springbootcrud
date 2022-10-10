package com.n56.Organization.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.n56.Organization.model.Country;
import com.n56.Organization.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

	Organization findByOrganizationName(String organizationName);

	Organization findByOrganizationCode(String organizationCode);

	Organization findByOrganizationNameAndOrganizationIdNot(String organizationName, Integer organizationId);

	Organization findByOrganizationCodeAndOrganizationIdNot(String organizationCode, Integer organizationId);

}