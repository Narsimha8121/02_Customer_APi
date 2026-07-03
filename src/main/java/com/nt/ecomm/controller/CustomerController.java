package com.nt.ecomm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nt.ecomm.dto.CustomerDto;
import com.nt.ecomm.dto.ResetPwdDto;
import com.nt.ecomm.response.ApiResponse;
import com.nt.ecomm.response.AuthResponse;
import com.nt.ecomm.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<String>> register(@RequestBody CustomerDto dto) {
		ApiResponse<String> response = new ApiResponse<>();
		Boolean emailUnique = customerService.isEmailUnique(dto.getEmail());
		if (emailUnique) {
			Boolean register = customerService.register(dto);
			if (register) {
				response.setStatus(201);
				response.setMessage("Sucess");
				response.setData("Registration Success");
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			} else {
				response.setStatus(500);
				response.setMessage("Failed");
				response.setData("Registration Failed");
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(500);
			response.setMessage("Failed");
			response.setData("Email already exit");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody CustomerDto dto) {
		ApiResponse<AuthResponse> response = new ApiResponse<>();
		AuthResponse authRep = customerService.login(dto);
		System.out.println("AUthResp---Controller---"+authRep);
		if (authRep != null) {
			response.setStatus(200);
			response.setMessage("Login Success");
			response.setData(authRep);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(400);
			response.setMessage("Invalid Credentials");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@Autowired
	private BCryptPasswordEncoder encoder;

	@PostMapping("/resetPwd")
	public ResponseEntity<ApiResponse<String>> resetPwd(@RequestBody ResetPwdDto dto) {
		ApiResponse<String> response = new ApiResponse<>();
		CustomerDto customer = customerService.getCustomerByEmail(dto.getEmail());
		System.out.println(customer.getPwd());
      System.out.println(dto.getOldPwd());
		
		if (!encoder.matches(dto.getOldPwd(), customer.getPwd())) {
			response.setStatus(400);
			response.setMessage("Failed");
			response.setData("Old pwd incorrect");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		Boolean status = customerService.resetPwd(dto);
		if (status) {
			response.setStatus(200);
			response.setMessage("Success");
			response.setData("Password updated");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMessage("Failed");
			response.setData("Pwd Reset Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/forgotpwd/{email}")
	public ResponseEntity<ApiResponse<String>> forgot(@PathVariable String email) {
		System.out.println(email);
		ApiResponse<String> response = new ApiResponse<>();
		Boolean status = customerService.forgotPwd(email);
		if (status) {
			response.setStatus(200);
			response.setMessage("Success");
			response.setData("Email send to Reset pwd");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMessage("Failed");
			response.setData("No Account found");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
