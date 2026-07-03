package com.nt.ecomm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.ecomm.entites.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	
	public Customer findByEmail(String email);
}
