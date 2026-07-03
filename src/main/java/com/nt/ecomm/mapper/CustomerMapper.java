package com.nt.ecomm.mapper;

import org.modelmapper.ModelMapper;

import com.nt.ecomm.dto.CustomerDto;
import com.nt.ecomm.entites.Customer;

public class CustomerMapper {

	private static ModelMapper mapper=new ModelMapper();
	
	public static Customer convertToEntity(CustomerDto dto) {
		return mapper.map(dto, Customer.class);
	}
	

	public static CustomerDto convertToDto(Customer customer) {
		return mapper.map(customer, CustomerDto.class);
	}
}
