package repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.vividsolutions.jts.geom.Point;

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
	@Query ( value = "FROM User u where u in ( SELECT ul.user FROM UserLocation ul WHERE ST_Distance_Sphere( ul.point, :userLoc) < :distance ) ")
	public Page<User> findAllLocation ( @Param("userLoc") Point userLoc, @Param("distance") int distance, Pageable p );
	
}
