package com.nt.ecomm.entites;

import java.util.Date;



import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="customer")
@Setter
@Getter
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	private String name;
	
	private String email;
	
	private String pwd;
	
	private String pwdUpdated;

	private String phoneNo;
	
	
	@CreationTimestamp
	private Date dateCreated;
	
	
	@UpdateTimestamp
	private Date lastUpdated;
	
	
	 @Enumerated(EnumType.STRING)
	    private Role role;
	
	
	
}
