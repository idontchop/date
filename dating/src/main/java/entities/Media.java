package entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Media
 *
 */
@Entity

public class Media implements Serializable {

	   
	@Id
	@GeneratedValue
	private long id;
	
	private static final long serialVersionUID = 1L;
	
	// Are we referring back?
	//private User user;
	
	@Lob
	private byte[] data;
	
	// will need an enum for this
	private int type;
	
	private Date created;
	
	private boolean active;
	
	// 0 = first, duplicates will be listed on first encountered
	// Sets the order the user wants the media to appear in profile
	private int priority;

	// another enum, lists stages of system approval of pic
	private int validated;
	
	public Media() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
   
}
