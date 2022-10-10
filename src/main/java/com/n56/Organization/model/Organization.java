package com.n56.Organization.model;

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
@Table(name = "organization")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Organization {

	@Id
	@Column(name = "organization_id")
	@SequenceGenerator(name = "organization_id_seq", sequenceName = "organization_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_id_seq")
	private Integer organizationId;

	@Column(name = "name")
	private String organizationName;

	@Column(name = "code")
	private String organizationCode;	

	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;

	@Column(name = "address_line3")
	private String addressLine3;

	@Column(name = "city")
	private String city; 
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

	@Column(name = "created_by")
	private int createdBy;

	@Column(name = "updated_by")
	private int modifiedBy;



}
