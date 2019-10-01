package com.idontchop.dating;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entities.Favorites;
import entities.User;
import repositories.UserRepository;
import repositories.FavoritesRepository;

/**
 * This is going to be a big project but keep reading and we will get there.
 * @author micro
 */
@SpringBootApplication (scanBasePackages = { "com.idontchop"} )
@RestController
@EnableAutoConfiguration
@EntityScan("entities")
@EnableJpaRepositories("repositories")
@ComponentScan (basePackages = { "adminMVC" } )
public class DatingApplication {


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FavoritesRepository fRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(DatingApplication.class, args);
	}
	
	@RequestMapping ("/")
	public String helloWorld () {
		return "Hello from future Dating App";
	}
		
	
	
	@RequestMapping ("/allFavorites")
	public Iterable<Favorites> allFavorites () {

		return fRepository.findAll(); 
	}
}
