package com.insysred.isp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class IspApplication {

	public static void main(String[] args) {
		SpringApplication.run(IspApplication.class, args);
	}

}
