package com.idontchop.dating;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import entities.Blocks;
import entities.Favorites;
import entities.Hides;
import entities.Interactions;
import entities.InteractionsId;
import entities.Likes;
import entities.Media;
import entities.User;
import entities.UserLocation;
import repositories.BlocksRepository;
import repositories.FavoritesRepository;
import repositories.HidesRepository;
import repositories.LikesRepository;
import repositories.MediaRepository;
import repositories.UserLocationRepository;
import repositories.UserRepository;
import service.MediaService;

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
	private MediaRepository mRepository;
	
	@Autowired
	private MediaService mService;
	
	long userList[] = { 51L, 54L, 57L, 63L, 66L, 69L, 72L, 75L, 78L, 81L, 84L, 85L, 86L, 87L, 88L };
	
	@Autowired
	private InteractionsService iRepository;
	
	@Autowired
	private InteractionsService iService;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Test
	public void getByFromTest () {
		User user = userRepository.findById(42L).get();
		List<Favorites> f = new ArrayList<>();
		fRepository.findAllByFrom(user).forEach( e -> f.add(e));
		assertTrue ( f.size() == 3);
		ObjectMapper om = new ObjectMapper();
		String s =  "";
		try {
			s = om.writeValueAsString(f.get(0));
		} catch (JsonProcessingException e1) {
			
			e1.printStackTrace();
		}
		System.out.println(s);
		
	}
	
	public void getInteractionsTest () {
		
		iService.findInteractions(42L, userList, IType.LIKE).forEach( e -> {
			System.out.print ( e.getClass().toString() );
			System.out.println ( ":" + e.getFrom().getId() + "_" + e.getTo().getId() );
		});
	}
	
	
	public void MediaTest () throws IOException {
		
		Iterable<Media> m = mRepository.findAll();
		
		m.forEach( e -> {
			System.out.println( e.getId() );
			if (e.getId() == 158 ) {
				System.out.println( e.getMediaData().getId());
			}
		});
	}
	
	public void MediaAddTest () throws IOException {
		
		try {
		Media m = new Media();
		File file = new File ("/home/nate/Pictures/satori.jpg");
		
		BufferedImage o = ImageIO.read(file);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(o, "jpg", baos);
		baos.flush();
		
		byte[] iByte = baos.toByteArray();
		baos.close();
		
		User user = userRepository.findById(42L).get();
		
		m.setData(iByte);
		m.setUser(user);
		
		System.out.println("Storing " + m.getId());
		mRepository.save(m);
		} catch ( Exception e ) {
			System.out.println (e.getMessage());
		}
	}
	

	
	
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
	
	
	public void LikesTest () {
		User from = ur(42L);
		User to = ur (57L);
		
		Likes like = new Likes(from, to);
		
		lRepository.save(like);
	}
	
	@Autowired HidesRepository hRepository;
	
	
	public void HidesTest () {
		User from = ur (42L);
		User to = ur (57L);
		
		Hides hide = new Hides ( from, to);
		hRepository.save(hide);		
	}
	
	@Autowired BlocksRepository bRepository;
	
	
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
		
		//Page<User> p = userRepository.findAllLocation(null, null, p2, 80000, PageRequest.of ( 0, 10 ) );
		//Page<User> p = userRepository.findAllCustom( PageRequest.of ( 0, 10 ));
		
		//assertTrue (p.size() > 0);
		//assertTrue ( p.getNumberOfElements() > 0);
	}

	

}
