package com.nt.ecomm.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nt.ecomm.entites.Customer;
import com.nt.ecomm.repo.CustomerRepo;
@Service
public class CustomerUserService implements UserDetailsService {
	
	@Autowired 
	CustomerRepo customerRep;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Customer c = customerRep.findByEmail(email);
		System.out.println(c.getEmail()+"----"+c.getPwd());
		return new User(c.getEmail(), c.getPwd(), Collections.emptyList());
	}

}
