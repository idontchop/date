package repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vividsolutions.jts.geom.Geometry;

import entities.UserLocation;

public interface UserLocationRepository extends CrudRepository<UserLocation, Long> {

	//@Query (value = "SELECT ul FROM user_location WHERE within(ul.point, :bounds) = true")
	//public List<UserLocation> findAllWithin ( @Param ("bounds") Geometry bounds);
}
