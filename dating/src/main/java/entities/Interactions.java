package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * super class for the User Interactions: likes, favorites, hides, blocks
 * 
 * These are used by a junction table.
 * 
 * These separate IDs are a composite Id. Which is necessary to avoid
 * duplicates and for faster searches whether searching for the composite
 * key or one individually.
 * 
 * ie: To check if someone is blocked, it will be looking for a composite key.
 * 
 * To list someone's favorites, it will be searching by one key.
 * 
 * The Id should be static in the user class. If we are changing Ids somewhere,
 * we are in trouble anyway. So setting this separate Id on the constructor
 * and setter is sufficient
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
@MappedSuperclass
@IdClass ( InteractionsId.class )
public class Interactions implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * These separate IDs are a composite Id. Which is necessary to avoid
	 * duplicates and for faster searches whether searching for the composite
	 * key or one individually.
	 * 
	 * ie: To check if someone is blocked, it will be looking for a composite key.
	 * 
	 * To list someone's favorites, it will be searching by one key.
	 * 
	 * The Id should be static in the user class. If we are changing Ids somewhere,
	 * we are in trouble anyway. So setting this seperate Id on the constructor
	 * and setter is suffecient
	 */
	@Id
	@Column(name = "from_id", insertable = false, updatable = false )
	private long fromId;

	@Id
	@Column( name = "to_id", insertable = false, updatable = false )
	private long toId;
	
	@ManyToOne ( fetch = FetchType.LAZY )
	@JoinColumn(name="from_id",insertable = false, updatable = false )
	@JsonIgnore
	private User from;
	
	@ManyToOne ( fetch = FetchType.LAZY )
	@JoinColumn(name="to_id",insertable = false, updatable = false )
	@JsonIgnore
	private User to;
		
	private boolean active = true;
	
	private Date created = new Date();
	
	public Interactions ( User from, User to ) {
		this.from = from;
		this.to = to;
		this.fromId = from.getId();
		this.toId = to.getId();
	}
	
	
	public User getFrom() {
		return from;
	}



	public void setFrom(User from) {
		this.from = from;
		this.fromId = from.getId();
	}



	public User getTo() {
		return to;
	}



	public void setTo(User to) {
		this.to = to;
		this.toId = to.getId();
	}



	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}



	public Date getCreated() {
		return created;
	}



	public void setCreated(Date created) {
		this.created = created;
	}


	public long getFromId() {
		return fromId;
	}


	public void setFromId(long fromId) {
		this.fromId = fromId;
	}


	public long getToId() {
		return toId;
	}


	public void setToId(long toId) {
		this.toId = toId;
	}


	public Interactions() {
		super();
	}
   
}

