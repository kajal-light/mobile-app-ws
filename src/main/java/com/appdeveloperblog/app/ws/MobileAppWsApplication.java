package com.appdeveloperblog.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appdeveloperblog.app.ws.ui.appproperti.AppProperties;
import com.appdeveloperblog.app.ws.ui.shared.dto.Spring.SpringApplicationsContext;

@SpringBootApplication
public class MobileAppWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileAppWsApplication.class, args);
		
		
		
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		
		return new BCryptPasswordEncoder();
	}
	@Bean
	
	
	public SpringApplicationsContext springApplicationContext() {
		
		
		return new SpringApplicationsContext();
	}
	@Bean(name="AppProperties")
	public AppProperties getAppProperties() {
		
		return new AppProperties();
		
		
	}
	
	
}



