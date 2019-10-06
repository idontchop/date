package entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;


/**
 * This entity holds the location where the user would like to be found.
 * May be a choice of the user's home city or geolocation from device.
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
@Entity
public class UserLocation {

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne ( fetch = FetchType.LAZY)
	@JoinColumn (name = "user_id")
	private User user;
	
	@Column ( columnDefinition = "Point")
	@JsonIgnore
	private Point point;
	
	private Date created = new Date();
	
	/**
	 * Set to true if this location was received
	 * from geolocation
	 */
	private boolean plotted = false;;

	/**
	 * Provides the Point class UserLocation needs
	 * from long + lat  LONG FIRST
	 * @param lng
	 * @param lat
	 * @return
	 * @throws ParseException 
	 */
	public static Point pointFromCoords( double lng, double lat) throws ParseException {
		
		return (Point) new WKTReader().read(String.format("POINT(%f %f)", lng, lat));
	}
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public boolean isPlotted() {
		return plotted;
	}

	public void setPlotted(boolean plotted) {
		this.plotted = plotted;
	}

	
}
