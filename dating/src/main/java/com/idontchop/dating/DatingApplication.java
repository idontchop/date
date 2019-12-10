package com.idontchop.dating;


import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;

import dto.UserDto;
import entities.Favorites;
import entities.Gender;
import entities.User;
import entities.UserLocation;
import entities.UserProfile;
import entities.UserSecurity;
import repositories.UserRepository;
import repositories.FavoritesRepository;
import repositories.GenderRepository;

/**
 * This is going to be a big project but keep reading and we will get there.
 * 
 * 12/9/19 - Linux upgrade took a turn for worse. Forced to setup Windows development.
 * 
 * @author micro
 */
@SpringBootApplication (scanBasePackages = { "com.idontchop"} )
@RestController
@EnableAutoConfiguration
@EntityScan({"entities"})
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
	
	/**
	 * 	
	 * Rest endpoint: /mainSearch
	 * 
	 * See: UserRepository.java
	 * 
	 * This is the main search for the user. The frontend is envisioned as an instagram-style
	 * endless scroll where listings are chosen based on search setting (location, gender, etc)
	 * 
	 * This endpoint should be kept as lean as possible. If the user wants to make more refined
	 * searches such as on traits or only show liked users, a new search endpoint should be
	 * used:  /refinedSearch
	 * 
	 * Parameters are supplied via Json.
	 * 
	 * @param minAge
	 * @param maxAge
	 * @param perPage technically not a profiles per page as that is determined by front end.
	 * 		This Int determines how many to retrieve at a time.
	 * @param page which page we are on, managed by frontend
	 * @param lat latitude of search area, may be determined by user location or a city
	 * @param lng longitude
	 * 
	 * @return Json with matching users
	 */
	@RequestMapping ("mainSearch")
	public Page<UserDto> mainSearch ( 
			@RequestParam (defaultValue = "10") Integer perPage, 	// Number of profiles per page
			@RequestParam (defaultValue = "0") Integer page,		// Current page number
			@RequestParam (defaultValue = "18") Integer minAge,		// Can default ages
			@RequestParam (defaultValue = "80") Integer maxAge,
			@RequestParam (defaultValue = "0") Double lat,			//user supplied latitude
			@RequestParam (defaultValue = "0") Double lng			// longitude
			) {
		
		// Setup pagingandsorting repository
		Pageable springPage = PageRequest.of ( page, perPage );
		
		// Get the user location using static method in UserLocation entity
		Point userLoc;
		try {
			userLoc = UserLocation.pointFromCoords(lng, lat);
		} catch (ParseException e) {
			// Some how bad location data
			return null;
		}
		
		// search distance set arbitrarily for now
		int searchDistance = 80000;
		
		// if user doesn't want to search by location
		if ( lat == 0 && lng == 0) searchDistance = 100000000;
		
		// get the user's gender preferences
		// Not set in a search form, set in the user's profile
		Gender gender = getUser().getGender();
		Gender interestedIn = getUser().getInterestedIn();
		
		return userRepository.findAllLocation( getUser(),
				gender, interestedIn, minAge, maxAge, userLoc, searchDistance, springPage).map( 
						user -> {
							UserDto userDto = new UserDto();
							userDto.fromEntity(user);
							return userDto;
						});
	}
		
	/**
	 * TODO: consolidate these endpoints into an Interactions DRY
	 * 
	 * @param perPage
	 * @param page
	 * @return
	 */
	@RequestMapping ("showLikes")
	public Page<User> showLikes (
			@RequestParam (defaultValue = "10") Integer perPage, 	// Number of profiles per page
			@RequestParam (defaultValue = "0") Integer page		// Current page number
			) {
		
		// Setup pagingandsorting repository
		Pageable p = PageRequest.of ( page, perPage );
		
		return userRepository.findAllLikes(getUser(), p);
	}
	
	@RequestMapping ("showFavorites")
	public Page<User> showFavorites (
			@RequestParam (defaultValue = "10") Integer perPage,
			@RequestParam (defaultValue = "0") Integer page
			) {
		
		// Setup pagingandsorting repository
		Pageable p = PageRequest.of ( page, perPage );
		
		return userRepository.findAllFavorites(getUser(), p);
		
	}
	
	/* End searches */
	
	/**
	 * To handle Dto - Entity conversion
	 * @return
	 */
	@Bean ( name = "modelMapper" )
	public ModelMapper modelMapper () {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DatingApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner () {
		return args -> {
			
			
			/* User and Profile 
			  
			String[][]  strings = new String[][] {
				{
					"profile for Carter", // profile.aboutme
					"I'm looking lady2", //profile.lookingfor
					"Carter", //profile.displayname
					"carter", //security.username
					"1234", // security.password
				},
				{
					"Melani the bloodsucker",
					"Looking lady that is beautiful2",
					"Melani",
					"melani",
					"1234"
				},
				{
					"Helen the crazy cutie",
					"Looking tall dark and handsome2",
					"Helen",
					"helen",
					"1234"
				},
				{
					"Beautiful lady2",
					"I want a rich man2",
					"Lumi",
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
			}*/
			
			// end add user CL
			 
			User u = userRepository.findById(60L).orElse(new User());
			userRepository.delete(u);

		};
	}
	
	@RequestMapping ("/")
	public String helloWorld () {
		return "Hello from future Dating App on /" + getUser().getProfile().getAge();
	}
	
	@RequestMapping ("/testApi")
	public Iterable<User> testApi() {
		return userRepository.findAll();
	}
	
	@RequestMapping ( "/User" )
	public Principal showUser (Principal principal) {
		return principal;
	}
	

	@RequestMapping ( "/findgender" )
	public Page<User> findgender () {
		return userRepository.findAllGender(genderRepository.findByName("Placeholder"), PageRequest.of ( 0, 10 ));
	}
	
	@RequestMapping ( "/findInterestedIn" )
	public Page<User> findInterestedIn() {
		return userRepository.findByInterest(
				genderRepository.findByName("Woman"),
				genderRepository.findByName("Man"),
				PageRequest.of ( 0, 10 ) );
	}
	
	@RequestMapping ( "/findByAge" )
	public Page<User> findByAge () {
		return userRepository.findByAgeRange(18, 38, PageRequest.of ( 0, 10 ));
		
	}

	
	@RequestMapping ("/allFavorites")
	public Iterable<Favorites> allFavorites () {

		return fRepository.findAll(); 
	}
	
	/**
	 * Helper function, returns the User entity of the currently logged in user
	 */
	private User getUser () {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		return ((CurrentUser) authentication.getPrincipal()).getUser();
	}	
}
