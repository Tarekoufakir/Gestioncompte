package com.example.demo.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Pour la gestion des utilisateurs et leurs roles
		
		PasswordEncoder pe = passwordEncoder();
		String mdpEncode = pe.encode("emsi");
		System.out.println(mdpEncode);
		
		auth.inMemoryAuthentication().withUser("tarek").password(mdpEncode).roles("USER");
		auth.inMemoryAuthentication().withUser("zoubair").password(mdpEncode).roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password(mdpEncode).roles("ADMIN");

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin().loginPage("/login");
		http.authorizeRequests().antMatchers("/operations","/consulterCompte").hasAnyRole("ADMIN","USER");
        http.authorizeRequests().antMatchers("/saveOperation").hasRole("ADMIN");
        http.exceptionHandling().accessDeniedPage("/403");
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}



	
	
	
}
