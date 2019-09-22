package com.idontchop.dating;

import service.SecurityConfigDating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import datingEntities.Gender;
import datingEntities.User;



@SpringBootApplication(scanBasePackages = {"com.idontchop"})
@RestController
@EnableAutoConfiguration
@ComponentScan("service")
public class DatingApplication {

	@Autowired
	private SecurityConfigDating c;
	

	@Autowired
	private Gender g;
	
	public static void main(String[] args) {
		SpringApplication.run(DatingApplication.class, args);
	}

	@RequestMapping ("/")
	public String helloWorld () {
		return "Hello World2";
	}
}
