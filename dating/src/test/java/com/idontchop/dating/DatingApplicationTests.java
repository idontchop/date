package com.idontchop.dating;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

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
	

	@Test
	public void LocationEntityTest () throws ParseException  {
		String slocVegas = "POINT(-115.172813 36.114647)";
		String slocLA = "POINT(-118.243683 34.052235)";
		String slocLondon = "POINT(-0.118092 51.509865)";
		String slocSydney = "POINT(151.2093 -33.8651)"; 
		
		WKTReader wr = new WKTReader();
		
		Point point = (Point) wr.read(slocVegas);
		
		UserLocation ul = new UserLocation();
		ul.setPoint(point);
		
		User user = userRepository.findById(42L).orElse(new User());
		ul.setUser(user);
		//userLocationRepository.save(ul);
		assertTrue ( userLocationRepository.findAllWithin(point).size() > 0);
		
		
	}
	
	@Test
	public void LocationPullTest () throws ParseException {
		String slocVegas = "POINT(-115.172813 36.114647)";
		String slocLA = "POINT(-118.243683 34.052235)";
		String slocLondon = "POINT(-0.118092 51.509865)";
		String slocSydney = "POINT(151.2093 -33.8651)"; 
		
		WKTReader wr = new WKTReader();
		
		Point point = (Point) wr.read(slocLondon);
		
		Point p2 = UserLocation.pointFromCoords(-115.2557502, 36.185885299999995);
		
		Page<User> p = userRepository.findAllLocation(p2, 80000, PageRequest.of ( 0, 10 ) );
		//Page<User> p = userRepository.findAllCustom( PageRequest.of ( 0, 10 ));
		
		//assertTrue (p.size() > 0);
		assertTrue ( p.getNumberOfElements() > 0);
	}

	

}
