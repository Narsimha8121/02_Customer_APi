package com.nt.ecomm.service;

import com.nt.ecomm.dto.CustomerDto;
import com.nt.ecomm.dto.ResetPwdDto;
import com.nt.ecomm.response.AuthResponse;

public interface CustomerService {
	
	public Boolean isEmailUnique(String email);

	public Boolean register(CustomerDto customerDto);
	
	public Boolean resetPwd(ResetPwdDto resetPwdDto);
	
	public CustomerDto getCustomerByEmail(String email);
	
	public AuthResponse login(CustomerDto customerDto);
	
	public Boolean forgotPwd(String email);
}
