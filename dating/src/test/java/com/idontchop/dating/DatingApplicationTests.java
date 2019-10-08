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

import entities.Blocks;
import entities.Favorites;
import entities.Hides;
import entities.Interactions;
import entities.InteractionsId;
import entities.Likes;
import entities.User;
import entities.UserLocation;
import repositories.BlocksRepository;
import repositories.FavoritesRepository;
import repositories.HidesRepository;
import repositories.LikesRepository;
import repositories.UserLocationRepository;
import repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatingApplicationTests {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserLocationRepository userLocationRepository;
	
	@Autowired
	private FavoritesRepository fRepository;
	
	Logger logger = LoggerFactory.getLogger(DatingApplicationTests.class);

	public void DatingApplicationTest () {
		
		String username = "Nate";
		User user = userRepository.findByUserSecurity_Username ( username );
		assertTrue ( "Nate" == user.getUserSecurity().getUsername() );
	}
	
	@Autowired
	private InteractionsService iRepository;
	
	@Test
	public void InteractionServiceTest () {
		
		User from = ur (72L);
		User to = ur (84L);
		
		IType.stream().forEach( i -> {
			
			if ( ! (i == IType.GIFT) ) {
				try {
					logger.debug( i.getName() + ": add");
					Interactions iAction = i.get(from, to);
					iRepository.write(iAction);
					logger.debug( i.getName() + ": retrieve");
					InteractionsId id = new InteractionsId(from.getId(), to.getId());
					iAction = iRepository.get(from.getId(), to.getId(), i);
					logger.debug( "retrieved: " + iAction.toString() + " - " + iAction.getFrom().getId() + "-" + iAction.getTo().getId());
					logger.debug( i.getName() + ": delete");
					iRepository.remove(from.getId(), to.getId(), i);
				} catch (Exception e) {
					logger.debug("caught: " + e.getMessage());
				}
				
			}
		});
	}
	
	@Test
	public void ITypeTest () {

		User from = ur (72L);
		User to = ur (81L);	
		
		
		
		for ( IType i : IType.values() ) {

			
			
			if ( i == IType.GIFT ) continue;
			Interactions newFav = i.get(from, to);			
			
			logger.debug ( i.getName() + ":" + newFav.getClass().getName() );
			assertTrue ( newFav.getTo().getId() == 81 && newFav.getFrom().getId() == 72 );

			iRepository.write(newFav);
			
		}

	}


	public void FavoritesTest () {

		User from = ur (42L);
		User to = ur (54L);
		Favorites fav = new Favorites();
		fav.setFrom(from);
		fav.setTo(to);
		
		fRepository.save(fav);
	}
	
	@Autowired LikesRepository lRepository;
	
	@Test
	public void LikesTest () {
		User from = ur(42L);
		User to = ur (57L);
		
		Likes like = new Likes(from, to);
		
		lRepository.save(like);
	}
	
	@Autowired HidesRepository hRepository;
	
	@Test
	public void HidesTest () {
		User from = ur (42L);
		User to = ur (57L);
		
		Hides hide = new Hides ( from, to);
		hRepository.save(hide);		
	}
	
	@Autowired BlocksRepository bRepository;
	
	@Test
	public void BlocksTest () {
		User from = ur (42L);
		User to = ur (57L);
		
		Blocks block = new Blocks ( from, to);
		bRepository.save(block);
	}
	
	/**
	 * grabs users for testing
	 * @param id
	 * @return
	 */
	private User ur ( long id ) {
		
		User from = userRepository.findById(id).get();

		assertTrue ( from.getId() == id);
		return from;
	}
	
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
