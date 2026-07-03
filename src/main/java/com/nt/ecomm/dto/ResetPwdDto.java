package com.nt.ecomm.dto;

import lombok.Data;

@Data
public class ResetPwdDto {
   private String name;
	
	private String email;
	
	private String oldPwd;
	
	private String newPwd;
	
	private String confimPwd;
}
