package com.fintech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FintechApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintechApplication.class, args);
	}

}
