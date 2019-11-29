package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.vividsolutions.jts.geom.Point;

import entities.Gender;
import entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUserSecurity_Username(String username);
	Page<User> findByGender_ActiveTrue( Pageable p );

	
	/**
	 * Query for the main search by location and distance from.
	 * @param userLoc
	 * @param distance
	 * @param p
	 * @return
	 */
	@Query ( value = "FROM User u where u.gender = :gender and "
			+ "u.interestedIn = :interestedIn and "
			+ "u in ( SELECT ul.user FROM UserLocation ul WHERE ST_Distance_Sphere( ul.point, :userLoc) < :distance ) ")
	public Page<User> findAllLocation (
			@Param("gender") Gender gender, @Param("interestedIn") Gender interestedIn,
		 	@Param("userLoc") Point userLoc, @Param("distance") int distance, 
			Pageable p );
	
	@Query ( value = "FROM User u where u.gender = :findGender")
	public Page<User> findAllGender
		( @Param("findGender") Gender findGender, Pageable p);
	
	@Query ( value = "FROM User u where u.gender = :gender and u.interestedIn = :interestedIn")
	public Page<User> findByInterest
		( Gender gender, Gender interestedIn, Pageable p );
	
	@Query ( value = "FROM User u where "
			+ "u.profile.age >= :minAge and "
			+ "u.profile.age <= :maxAge")
	public Page<User> findByAgeRange
		( int minAge, int maxAge, Pageable p );
	
/*	@Query ( value = "SELECT u, TIMESTAMPDIFF(YEAR, u.profile.birthday, CURDATE()) as age FROM User u where "
			+ " age >= :minAge and "
			+ " age <= :maxAge")
	public Page<User> findByAgeRange2
		( int minAge, int maxAge, Pageable p );*/
}
