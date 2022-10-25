package com.n56.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.n56.organization.model.OrganizationUser;
@Repository
public interface OrganizationUserRepository extends JpaRepository<OrganizationUser, Integer>{

	
}
