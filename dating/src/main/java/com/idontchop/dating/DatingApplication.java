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


@SpringBootApplication
@RestController
@EnableAutoConfiguration
@EntityScan("entities")
@EnableJpaRepositories("repositories")
public class DatingApplication {


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FavoritesRepository fRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(DatingApplication.class, args);
	}

	@RequestMapping ("/")
	public Iterable<Favorites> helloWorld () {

		return fRepository.findAll(); 
	}
}
