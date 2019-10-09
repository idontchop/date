package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity implementation class for Entity: Media
 * 
 * Note the handling of MediaData -- the actual image byte data. It is
 * stored in another table MediaData to keep the Blob from being fetched
 * when only the info on the image is needed. Also will allow easy
 * separation of services later.
 * 
 * MediaData has two getters and setters
 * 
 * getData / setData: call the MediaData getter/setter directly for byte[]
 * 
 * getMediaData / setMediaData: handle the object
 * 
 * This separation made to handle Lazy Proxy Exceptions
 *
 */
@Entity

public class Media implements Serializable {

	   
	@Id
	@GeneratedValue
	private long id;
	
	
	@ManyToOne (fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn ( name = "user_id" )
	@JsonIgnore
	private User user;
	
	private static final long serialVersionUID = 1L;
	
	@OneToOne ( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	@JoinColumn ( name = "media_data" )
	@JsonIgnore
	private MediaData data;
	
	// will need an enum for this
	private int type = 0;
	
	private Date created = new Date();
	
	private boolean active = true;
	
	// 0 = first, duplicates will be listed on first encountered
	// Sets the order the user wants the media to appear in profile
	private int priority = 0;

	// another enum, lists stages of system approval of pic
	private int validated = 0;
	
	public Media() {
		super();
	}   
	
	/**
	 * Default constructor for adding default image, accepting default values
	 * Creates the MediaData object and table entry as well
	 * @param data
	 */
	public Media (byte[] data) {
		this.data = new MediaData();
		this.data.setData(data);
	}
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets byte from MediaData Relation
	 * @return
	 */
	@JsonIgnore
	public byte[] getData() {
		return data.getData();
	}
	
	/**
	 * Sets the MediaData. Will instantiate if doesn't already exist.
	 * @param data
	 */
	public void setData(byte[] data) {
		if ( this.data == null )
			this.data = new MediaData();
		this.data.setData(data);
	}
	
	@JsonIgnore
	public MediaData getMediaData () {
		return data;
	}
	public void setMediaData(MediaData data) {
		this.data = data;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getValidated() {
		return validated;
	}
	public void setValidated(int validated) {
		this.validated = validated;
	}
   
}
