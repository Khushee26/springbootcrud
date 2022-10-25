package com.n56.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.n56.organization.model.MstUser;

public interface MstUserRepository extends JpaRepository<MstUser, Integer> {

	MstUser findByEmailIgnoreCase(String organizationUserEmail);
	
	MstUser findByEmailIgnoreCaseAndUserIdNot(String organizationUserEmail, Integer userId);

}
