package com.project.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class ProductWithDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductWithDbApplication.class, args);
	}

	/**
	 * if we use CommonsMultipart then need to create bean of CommonsMultipartResolver class
	 * if we use MultipartFile then no need to create a bean
	 * for CommonsMultipart annotation use is of @Requestparam
	 * for Multipart annotation use is of @Requestpart
	 */
	@Bean
	public CommonsMultipartResolver commonsMultipartResolver() {
		return new CommonsMultipartResolver();

	}
 
}
