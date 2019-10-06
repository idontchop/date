package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.geo.Point;

import com.vividsolutions.jts.io.WKTReader;

/**
 * This entity holds the location where the user would like to be found.
 * May be a choice of the user's home city or gealocation from device.
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
@Entity
public class UserLocation {

	@Id
	@GeneratedValue
	private long id;
	
	private Point point;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
	public void setPointByPoints ( double p1, double p2 ) {
		this.point = new Point ( p1, p2 );
	}
}
