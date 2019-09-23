package entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

/**
 * The purpose of this entry is mainly to determine the amount of requests a user has
 * made in a certain amount of time, to limit data mining and protect against performance issues.
 *
 */
@Entity
@Table ( name = "request" )
public class Request implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne ( fetch = FetchType.LAZY )
	@JoinColumn ( name = "user_id" )
	private User user;
	
	@ManyToOne ( fetch = FetchType.EAGER )
	@JoinColumn ( name = "type" )
	private RequestType type;
	
	private Date created;
	
	public Request() {
		super();
	}
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
   
}
