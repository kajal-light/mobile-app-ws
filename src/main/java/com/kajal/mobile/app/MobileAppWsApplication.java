package com.kajal.mobile.app;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kajal.mobile.app.ui.appproperti.AppProperties;
import com.kajal.mobile.app.ui.shared.dto.Spring.SpringApplicationsContext;

@SpringBootApplication
public class MobileAppWsApplication extends SpringBootServletInitializer {

	@Override
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		return application.sources(MobileAppWsApplication.class);
		
		
	}
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



