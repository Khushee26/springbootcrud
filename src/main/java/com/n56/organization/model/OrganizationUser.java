
package com.n56.organization.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "organization_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString	


public class OrganizationUser {

	@Id
	@Column(name = "organization_user_id")
	@SequenceGenerator(name = "organization_user_id_seq", sequenceName = "organization_user_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_user_id_seq")
	private Integer organizationUserId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private MstUser mstUser;

	@ManyToOne
	@JoinColumn(name = "organization_id")
	private Organization organization;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;

	@Column(name = "address_line3")
	private String addressLine3;

	@Column(name = "city")
	private String city;

	@ManyToOne()
	@JoinColumn(name = "country_id")
	private Country country;

	@Column(name = "active")
	private boolean active;

	@Column(name = "created_by")
	private int createdBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_by")
	private int updatedBy;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

}
