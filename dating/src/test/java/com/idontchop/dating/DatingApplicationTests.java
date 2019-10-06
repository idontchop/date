package com.idontchop.dating;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import entities.Favorites;
import entities.User;
import entities.UserLocation;
import repositories.FavoritesRepository;
import repositories.UserLocationRepository;
import repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatingApplicationTests {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserLocationRepository userLocationRepository;
	
	Logger logger = LoggerFactory.getLogger(DatingApplicationTests.class);

	public void DatingApplicationTest () {
		
		String username = "Nate";
		User user = userRepository.findByUserSecurity_Username ( username );
		assertTrue ( "Nate" == user.getUserSecurity().getUsername() );
	}
	

	public void LocationEntityTest ()  {
		String sloc = "POINT(36.114647 -115.172813)";



		
		
		
		
	}

	

}
