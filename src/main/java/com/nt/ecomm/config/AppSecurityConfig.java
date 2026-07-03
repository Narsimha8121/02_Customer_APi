package com.nt.ecomm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nt.ecomm.service.CustomerUserService;

import lombok.SneakyThrows;

@EnableWebSecurity
@Configuration
public class AppSecurityConfig {


	
	@Autowired
	CustomerUserService userService;

	@Bean
	public BCryptPasswordEncoder pwdencoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		System.out.println(userService);
		 authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(pwdencoder());
		System.out.println(authProvider);
		return authProvider;
	}

	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		AuthenticationManager config1 = config.getAuthenticationManager();
		System.out.println("----1---"+config1);
		return config1;
	}

	
	@Bean
	public SecurityFilterChain security(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(req -> {
			req.requestMatchers("/register", "/resetPwd", "/forgotpwd/*", "/login").permitAll().anyRequest()
					.authenticated();
		});
		return http.csrf().disable().build();
	}
}
