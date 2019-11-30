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
	 * TODO: hides and blocks search probably not efficient. Subquery best way?
	 * @param currentUser
	 * @param gender
	 * @param interestedIn
	 * @param minAge
	 * @param maxAge
	 * @param userLoc
	 * @param distance
	 * @param p
	 * @return
	 */
	@Query ( value = 
			"FROM User u where u.gender = :gender and "
			+ "u.interestedIn = :interestedIn and "
			// ^ Gender preferences set by user in profile
			+ "u.profile.age >= :minAge and u.profile.age <= :maxAge and "
			// ^ Age preferences set by user in search bar
			+ "u in ( SELECT ul.user FROM UserLocation ul WHERE ST_Distance_Sphere( ul.point, :userLoc) < :distance ) and " 
			// ^ Location preferences set by user in search bar
			+ "u not in ( SELECT hides.to FROM Hides hides WHERE hides.from = :currentUser and hides.to = u and hides.active = true) and " 
			// ^ Don't show hides in main search
			+ "u not in ( SELECT blocks.to FROM Blocks blocks WHERE blocks.from = :currentUser and blocks.to = u and blocks.active = true) ")
	public Page<User> findAllLocation (
			@Param("currentUser") User currentUser,
			@Param("gender") Gender gender, 
			@Param("interestedIn") Gender interestedIn,			
			@Param("minAge") int minAge, 
			@Param("maxAge") int maxAge,
		 	@Param("userLoc") Point userLoc, 
		 	@Param("distance") int distance, 
			Pageable p );
	
	/**
	 * When the user wants to filter by his own likes
	 * 
	 * TODO: sort by like date?
	 * 
	 * @param currentUser
	 * @param p
	 * @return
	 */
	@Query ( value = 
			"FROM User u WHERE "
			+ "u in ( SELECT likes.to FROM Likes likes WHERE likes.from = :currentUser and likes.active = true )" )					
	public Page<User> findAllLikes (
			@Param("currentUser") User currentUser,
			Pageable p );
	
	@Query ( value = 
			"FROM User u WHERE "
			+ "u in (SELECT favorites.to FROM Favorites favorites WHERE favorites.from = :currentUser and favorites.active = true )" )
	public Page<User> findAllFavorites (
			@Param("currentUser") User currentUser,
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
