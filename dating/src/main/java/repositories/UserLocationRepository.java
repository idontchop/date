package repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vividsolutions.jts.geom.Point;

import entities.UserLocation;

public interface UserLocationRepository extends CrudRepository<UserLocation, Long> {

	//@Query (value = "SELECT ul FROM user_location WHERE ST_Distance(ul.point, :userLoc) < 50")
	@Query ( value = "FROM UserLocation u WHERE ST_Distance( u.point, :userLoc) < 50 ORDER BY ST_Distance( u.point, :userLoc)")	
	public List<UserLocation> findAllWithin ( @Param("userLoc") Point userLoc);
}
