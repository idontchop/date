package com.idontchop.dating;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import entities.Favorites;
import entities.Gender;
import entities.User;
import entities.UserProfile;
import entities.UserSecurity;
import repositories.UserRepository;
import repositories.FavoritesRepository;
import repositories.GenderRepository;

/**
 * This is going to be a big project but keep reading and we will get there.
 * @author micro
 */
@SpringBootApplication (scanBasePackages = { "com.idontchop"} )
@RestController
@EnableAutoConfiguration
@EntityScan("entities")
@EnableJpaRepositories("repositories")
@ComponentScan (basePackages = { "com.idontchop" } )
@CrossOrigin (origins = "http://localhost:3000/", allowedHeaders = "*")
public class DatingApplication {


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FavoritesRepository fRepository;
	
	@Autowired
	private GenderRepository genderRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(DatingApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner () {
		return args -> {
			
			
			/* User and Profile
			  
			String[][]  strings = new String[][] {
				{
					"I'm a man2", // profile.aboutme
					"I'm looking lady2", //profile.lookingfor
					"Boomer2", //profile.displayname
					"boomer12", //security.username
					"1234", // security.password
				},
				{
					"Another man2",
					"Looking lady that is beautiful2",
					"Sooner2",
					"sooner12",
					"1234"
				},
				{
					"Tranny man2",
					"Looking tall dark and handsome2",
					"tranny2",
					"tranny12",
					"1234"
				},
				{
					"Beautiful lady2",
					"I want a rich man2",
					"lady2",
					"lady12",
					"1234"
				}
			};
			for ( int i = 0; i < strings.length; i++) {
			User u = new User ();
			u.setCreated(new Date());
			u.setLastLogin(new Date());
			
			// profile
			UserProfile up = new UserProfile ();
			up.setAboutMe(strings[i][0]);
			up.setLookingFor(strings[i][1]);
			up.setDisplayName(strings[i][2]);

			up.setBirthday(new Date());
			
			u.setProfile(up);
			
			// security
			UserSecurity us = new UserSecurity ();
			us.setUsername (strings[i][3]);
			us.setPassword(strings[i][4]);
			
			u.setUserSecurity(us);
			
			// gender
			Gender defaultGender = genderRepository.findById(-1L).orElse(Gender.getDefault());
			u.setGender(defaultGender);
			u.setInterestedIn(defaultGender);
			
			userRepository.save(u);
			}
			*/
			// end add user CL
			 
			User u = userRepository.findById(60L).orElse(new User());
			userRepository.delete(u);

		};
	}
	
	@RequestMapping ("/")
	public String helloWorld () {
		return "Hello from future Dating App";
	}
	
	@RequestMapping ("/testApi")
	public Iterable<User> testApi() {
		return userRepository.findAll();
	}
	
	@RequestMapping ("mainSearch")
	public Page<User> mainSearch ( 
			@RequestParam (defaultValue = "10") Integer perPage, // Number of profiles per page
			@RequestParam (defaultValue = "0") Integer page			// Current page number
			) {
		
		Pageable p = PageRequest.of ( page, perPage );
		return userRepository.findByGender_ActiveTrue(p);
	}
		
	
	
	@RequestMapping ("/allFavorites")
	public Iterable<Favorites> allFavorites () {

		return fRepository.findAll(); 
	}
}
