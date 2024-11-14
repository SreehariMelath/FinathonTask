package com.finathon.custom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	private final UserDetailsService userDetailsService;

	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// Use requestMatchers instead of antMatchers
				.authorizeRequests().requestMatchers("/login", "/register").permitAll() // Allow login and register
																						// without authentication
				.requestMatchers("/hello").authenticated() // Protect the hello page
				.and()
				// Configure form login
				.formLogin().loginPage("/login") // Specify the login page
				.defaultSuccessUrl("/hello", true) // Redirect to /hello after successful login
				.failureUrl("/login?error=true") // Redirect back to /login on login failure
				.permitAll().and()
				// Configure logout
				.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout=true") // Redirect to /login after
																						// successful logout
				.permitAll().and()
				// Optional: Enable session management
				.sessionManagement().invalidSessionUrl("/login?invalidSession=true"); // Redirect if session is invalid
																						// or expired

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder()) // Configure password encoder
				.and().build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
