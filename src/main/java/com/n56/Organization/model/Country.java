package com.n56.Organization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Country")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Country {

	@Id
	@Column(name = "country_id")
	@SequenceGenerator(name = "country_id_seq", sequenceName = "country_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_id_seq")
	private int countryId;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "country_code")
	private String countryCode;
	
	@Column(name = "created_by")
	private int createdBy;
	
	@Column(name = "modified_by")
	private int modifiedBy;


}
