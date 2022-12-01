package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.entity.Account;
import com.example.demo.util.LoginState;

@ComponentScan
// @EnableJpaRepositories(basePackages = "com.example.demo.repository")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class} )
public class DemoSpringBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoSpringBootApplication.class, args);

		LoginState.currentAccount = new Account();

		
	}
}
