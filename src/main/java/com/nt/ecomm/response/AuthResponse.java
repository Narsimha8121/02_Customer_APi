package com.nt.ecomm.response;

import com.nt.ecomm.dto.CustomerDto;

import lombok.Data;
@Data
public class AuthResponse {

	private CustomerDto customer;
	
	private String token;
}
