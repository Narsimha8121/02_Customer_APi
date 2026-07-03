package com.nt.ecomm.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.ecomm.dto.CustomerDto;
import com.nt.ecomm.dto.ResetPwdDto;
import com.nt.ecomm.entites.Customer;
import com.nt.ecomm.mapper.CustomerMapper;
import com.nt.ecomm.repo.CustomerRepo;
import com.nt.ecomm.response.AuthResponse;

@Service
public class CustomerServicerImpl implements CustomerService {

	@Autowired
	CustomerRepo customerRep;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	EmailService emailService;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	CustomerUserService service;

	@Override
	public Boolean isEmailUnique(String email) {
		// TODO Auto-generated method stub
		Customer c = customerRep.findByEmail(email);
		return c == null;
	}

	@Override
	public Boolean register(CustomerDto customerDto) {
		// TODO Auto-generated method stub

		String orginalPwd = generateRandomPwd();
		String encodePwd = encoder.encode(orginalPwd);

		Customer entitty = CustomerMapper.convertToEntity(customerDto);
		entitty.setPwd(encodePwd);
		entitty.setPwdUpdated("NO");
		Customer savedEntity = customerRep.save(entitty);

		// send email with temp
		if (savedEntity.getId() != null) {
			String subject = "Registration Success";
			String body = "Your Login Pwd " + orginalPwd;
			emailService.sendEmail(customerDto.getEmail(), subject, body);
			return true;

		}
		return false;
	}

	@Override
	public Boolean resetPwd(ResetPwdDto resetPwdDto) {
		// TODO Auto-generated method stub
		Customer c = customerRep.findByEmail(resetPwdDto.getEmail());
		if (c != null) {
			String newPwd = resetPwdDto.getNewPwd();
			String encodePwd = encoder.encode(newPwd);
			c.setPwd(encodePwd);
			c.setPwdUpdated("YES");
			customerRep.save(c);
			return true;
		}
		return false;
	}

	@Override
	public CustomerDto getCustomerByEmail(String email) {
		// TODO Auto-generated method stub
		Customer c = customerRep.findByEmail(email);
		if (c != null) {
			return CustomerMapper.convertToDto(c);
		}
		return null;

	}

	@Override
	public AuthResponse login(CustomerDto customerDto) {
		// TODO Auto-generated method stub

		AuthResponse response = null;
		UsernamePasswordAuthenticationToken authTOken = new UsernamePasswordAuthenticationToken(customerDto.getEmail(),
				customerDto.getPwd());
		System.out.println("Service  token-"+authTOken);
		System.out.println("Service  authManager-"+authManager);
		Authentication authenicate = authManager.authenticate(authTOken);
		System.out.println(authenicate);
		if (authenicate.isAuthenticated()) {
			response = new AuthResponse();
			Customer c = customerRep.findByEmail(customerDto.getEmail());
			response.setCustomer(CustomerMapper.convertToDto(c));
			response.setToken("");
		}
		return response;
	}

	Random rnd = new Random();

	private String generateRandomPwd() {
		String saltCHars = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder pwd = new StringBuilder();

		while (pwd.length() < 5) {
			int index = (int) (rnd.nextFloat() * saltCHars.length());
			pwd.append(saltCHars.charAt(index));
		}

		return pwd.toString();

	}

	@Override
	public Boolean forgotPwd(String email) {
		// TODO Auto-generated method stub
		Customer c = customerRep.findByEmail(email);
		if (c != null) {
			String subject = "Reset Pwd Request";
			String body = "temp body";
			emailService.sendEmail(email, subject, body);
			return true;
		}
		return false;
	}
}
