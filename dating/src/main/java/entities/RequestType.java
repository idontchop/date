package entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

import org.hibernate.validator.constraints.Length;

/**
 * Type of requests. Used by Request.
 * 
 * Using this can allow a good model of a user's activities.
 *
 */
@Entity
@Table ( name = "request_type" )
public class RequestType implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue
	private long id;

	@Length ( min = 4, max = 16 )
	private String name;
	
	private Date created;

	public RequestType() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
   
}
